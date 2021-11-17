package springStudy.board.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Board;
import springStudy.board.domain.Posting;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.PostingType;
import springStudy.board.domain.enums.UserRank;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BoardRepositoryTest {
    @Autowired private BoardRepository boardRepository;
    @Autowired private EntityManager em;
    @Autowired private PostingRepository postingRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommentRepository commentRepository;

    @Test
    @DisplayName("게시판 생성 후 조회하기")
    void createBoard() {
        Board board = new Board("자유게시판");
        boardRepository.save(board);

        em.flush();
        em.clear();

        Board findBoard = boardRepository.findBoard(board.getId());
        Assertions.assertThat(board.getName()).isEqualTo(findBoard.getName());

    }

    @Test
    @DisplayName("게시판 생성 후 포스팅")
    void createPosting() {
        // "자유 게시판" 생성
        Board board = new Board("자유게시판");
        boardRepository.save(board);

        // User 김재준 생성
        User user = new User("김재준", "rlawowns97@gmail.com",
                "1234pw", "공돌이", UserRank.NORMAL);
        userRepository.save(user);

        // User 김재준이 "자유 게시판"에 Posting 생성
        Posting posting = new Posting(board, user, "야식 추천좀", "",
                "제곧내", PostingType.FREE);
        postingRepository.save(posting);

        em.flush();
        em.clear();

        Posting findPosting = postingRepository.findOne(posting.getId());
        Assertions.assertThat(findPosting.getBoard().getName()).isEqualTo("자유게시판");
        Assertions.assertThat(findPosting.getTitle()).isEqualTo("야식 추천좀");
        Assertions.assertThat(findPosting.getContent()).isEqualTo("제곧내");
        Assertions.assertThat(findPosting.getUser().getNickName()).isEqualTo("공돌이");
    }
}