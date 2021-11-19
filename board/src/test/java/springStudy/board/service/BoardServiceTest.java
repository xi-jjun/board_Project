package springStudy.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Board;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.UserRank;
import springStudy.board.repository.UserRepository;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private BoardService boardService;
    @Autowired private UserService userService;

    @Test
    @DisplayName("중복된 이름은 생성 불가")
    void createBoard() {
        Board board = new Board("자유 게시판");
        boardService.createBoard(board);
        Board board1 = new Board("자유 게시판");
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> boardService.createBoard(board1));
        Assertions.assertThat(e.getMessage()).isEqualTo("중복된 이름의 게시판입니다");
    }

    @Test
    @DisplayName("관리자가 게시판 비활성화")
    void deactivateBoard() {
        // given
        Board board1 = new Board("자유 게시판");
        boardService.createBoard(board1);

        User user = new User("김재준", "이메일", "123", "공돌이", UserRank.ADMIN);
        userService.join(user);

        em.flush();
        em.clear();

        User findUser = userService.findUserByNickName("공돌이");
        Board findBoard = boardService.selectBoard(1L);

        // when
        boardService.changeActive(findUser.getId(), findBoard.getId());

        // then
        Assertions.assertThat(findBoard.isActive()).isEqualTo(false);
    }

    @Test
    @DisplayName("일반 사용자가 게시판을 비활성화 하려고 했을 때 - Exception")
    void deactivateNormalUser() {
        // given
        Board board1 = new Board("자유 게시판");
        boardService.createBoard(board1);

        User user = new User("김재준", "이메일", "123", "공돌이", UserRank.NORMAL);
        userService.join(user);

        em.flush();
        em.clear();

        User findUser = userService.findUserByNickName("공돌이");
        Board findBoard = boardService.selectBoard(1L);

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> boardService.changeActive(findUser.getId(), findBoard.getId()));

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("관리자 권한이 없습니다");
        Assertions.assertThat(findBoard.isActive()).isEqualTo(true);
    }
}