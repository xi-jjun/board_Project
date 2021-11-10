package springStudy.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Board;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.BoardType;
import springStudy.board.repository.BoardRepository;
import springStudy.board.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired private BoardRepository boardRepository;
    @Autowired private BoardService boardService;
    @Autowired private UserRepository userRepository;
    @Autowired private EntityManager em;

    @Test
    void create() {
        User creator = new User("재준", "공돌이", "123pw", "kjj@gmail.com");
        Board board = new Board("제목1", "부제목1",
                "내용111", BoardType.FREE, creator);

        boardService.createContent(board);
        Board findOne = boardService.findOne(board.getIdx());
        Assertions.assertThat(findOne).isEqualTo(board);
    }

    @Test
    void remove() {
        User creator = new User("재준", "공돌이", "123pw", "kjj@gmail.com");
        Board board = new Board("제목1", "부제목1",
                "내용111", BoardType.FREE, creator);
        boardService.createContent(board);
        boardService.removeContent(board.getIdx());
        Assertions.assertThat(boardService.findOne(board.getIdx())).isNull();
    }

    @Test
    void removeIfAlreadyRemoved() {
        User creator = new User("재준", "공돌이", "123pw", "kjj@gmail.com");
        Board board = new Board("제목1", "부제목1",
                "내용111", BoardType.FREE, creator);

        boardService.createContent(board);
        boardService.removeContent(board.getIdx()); // already remove

        IllegalStateException e = org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
                ()->boardService.removeContent(board.getIdx())); // remove again

        Assertions.assertThat(e.getMessage()).isEqualTo("Contents is not existed");
    }

    @Test
    void findByNickName() {
        User creator = new User("재준", "공돌이", "123pw", "kjj@gmail.com");
        Board board1 = new Board("제목1", "부제목1",
                "내용111", BoardType.FREE, creator);
        Board board2 = new Board("제목12", "부제목12",
                "내용111222", BoardType.FREE, creator);
        Board board3 = new Board("제목13", "부제목13",
                "내용1113333", BoardType.FREE, creator);
        userRepository.save(creator);
        boardService.createContent(board1);
        boardService.createContent(board2);
        boardService.createContent(board3);

        List<Board> boards = boardService.findBoardsByNickName(creator.getNickName());
        for (Board board : boards) {
            System.out.println("board = " + board);
        }

        Assertions.assertThat(3).isEqualTo(boards.size());
    }

    @Test
    void boardUpdate() {
        User creator = new User("재준", "공돌이", "123pw", "kjj@gmail.com");
        Board board1 = new Board("제목1", "부제목1",
                "내용111", BoardType.FREE, creator);
        userRepository.save(creator);
        boardService.createContent(board1);

        em.flush(); // query
        em.clear(); // cash clear

        User findUser = userRepository.findOne(creator.getIdx());
        Board findBoard = boardService.findOne(board1.getIdx());
        findBoard.updateBoard("다른 제목", "다른 부제목", "다른 내용", BoardType.NOTICE);

        em.flush();
        em.clear();

        Assertions.assertThat(findBoard.getContent()).isEqualTo(boardService.findOne(findBoard.getIdx()).getContent());
    }
}