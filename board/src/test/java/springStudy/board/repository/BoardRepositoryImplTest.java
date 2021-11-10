package springStudy.board.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Board;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.BoardType;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryImplTest {
    @Autowired private BoardRepository boardRepository;
    @Autowired private UserRepository userRepository;

    @Test
    void createNewContent() {
        // User
        User creator = new User("재준", "공돌이", "123pw", "kjj@gmail.com");
        Board board = new Board("제목1", "부제목1",
                "내용111", BoardType.FREE, creator);
        userRepository.save(creator);
        boardRepository.save(board);

        Board findBoard = boardRepository.findById(board.getIdx());
        Assertions.assertThat(findBoard).isEqualTo(board);
    }

    @Test
    void updateContents() {
        // User
        User creator = new User("재준", "공돌이", "123pw", "kjj@gmail.com");
        Board board = new Board("제목1", "부제목1",
                "내용111", BoardType.FREE, creator);
        userRepository.save(creator);
        boardRepository.save(board);

        Board findBoard = boardRepository.findById(board.getIdx());
        findBoard.updateBoard("다른제목", "다른부제목",
                "다른 내용", BoardType.NOTICE);
        System.out.println("findBoard = " + findBoard);
        Assertions.assertThat(board.getIdx()).isEqualTo(findBoard.getIdx());
        Assertions.assertThat("다른제목").isEqualTo(findBoard.getTitle());
    }

    @Test
    void removeContents() {
        // User
        User creator = new User("재준", "공돌이", "123pw", "kjj@gmail.com");
        Board board = new Board("제목1", "부제목1",
                "내용111", BoardType.FREE, creator);
        userRepository.save(creator);
        boardRepository.save(board);

        System.out.println("board = " + boardRepository.findById(board.getIdx()));
        boardRepository.remove(board.getIdx());
        Assertions.assertThat(boardRepository.findById(board.getIdx())).isNull();
        System.out.println("board = " + boardRepository.findById(board.getIdx()));
    }
}