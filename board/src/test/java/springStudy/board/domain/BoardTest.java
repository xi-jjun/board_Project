package springStudy.board.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.enums.PostingType;
import springStudy.board.domain.enums.UserRank;

@Transactional
class BoardTest {

    @Test
    void createBoard() {
        Board board = new Board("익명게시판");
        Assertions.assertThat("익명게시판").isEqualTo(board.getName());
    }

    @Test
    void createPosting() {
        Board board = new Board("자유게시판");
        User user = new User("김재준", "rlawowns97@gmail.com",
                "1234pw", "공돌이", UserRank.NORMAL);
        Posting posting = new Posting(board, user, "야식 추천좀", "",
                "제곧내", PostingType.FREE);

        Assertions.assertThat(posting.getTitle()).isEqualTo("야식 추천좀");
        Assertions.assertThat(posting.getBoard().getName()).isEqualTo("자유게시판");
        Assertions.assertThat(posting.getUser().getName()).isEqualTo("김재준");
    }

    @Test
    void createComment() {
        Board board = new Board("질문게시판");
        User user = new User("김재준1", "rlawowns971@gmail.com",
                "1234pw", "공돌이1", UserRank.NORMAL);
        Posting posting = new Posting(board, user, "야식 추천좀", "",
                "제곧내", PostingType.FREE);

        Comment comment = new Comment(posting, user, "죽어라 김재준!!");

        Assertions.assertThat(comment.getContent()).isEqualTo("죽어라 김재준!!");
        Assertions.assertThat(comment.getUser().getNickName()).isEqualTo("공돌이1");
        Assertions.assertThat(comment.getPosting().getTitle()).isEqualTo("야식 추천좀");
        Assertions.assertThat(comment.getPosting().getBoard().getName()).isEqualTo("질문게시판");

    }

}