package springStudy.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Board;
import springStudy.board.domain.Comment;
import springStudy.board.domain.Posting;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.PostingType;
import springStudy.board.domain.enums.UserRank;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class CommentServiceTest {
    @Autowired private CommentService commentService;
    @Autowired private PostingService postingService;
    @Autowired private BoardService boardService;
    @Autowired private UserService userService;
    @Autowired private EntityManager em;

    @BeforeEach
    @Disabled
    void createDefault() {
        Board board = new Board("자유 게시판");
        boardService.createBoard(board);

        User user = new User("김재준", "이메일", "123", "공돌이", UserRank.NORMAL);
        userService.join(user);

        Posting posting = new Posting(board, user, "야식 추천좀", "", "제곧내", PostingType.FREE);
        postingService.createPosting(posting);

        User commenter = new User("댓쓴이", "e", "123", "닉넴", UserRank.NORMAL);
        userService.join(commenter);

        Comment comment = new Comment(posting, commenter, "치킨이 가장 맛있어요!");
        commentService.addComment(comment);
    }

    @Test
    @DisplayName("댓글 쓰기")
    void createComment() {
        Board board;
        List<Board> boards = boardService.showAll();
        for (Board b : boards) {
            if(b.getName().equals("자유 게시판")) board = b;
        }
        User poster = userService.findUserByNickName("공돌이");
        Posting posting = new Posting();
        List<Posting> userPostings = postingService.getUserPostings(poster.getId());
        for (Posting userPosting : userPostings) {
            if(userPosting.getTitle().equals("야식 추천좀")) posting = userPosting;
        }

        // when
        Comment comment = new Comment(posting, poster, "치킨이 가장 맛있어요!");
        commentService.addComment(comment);

        em.flush();
        em.clear();

        Comment findComment = commentService.getOneComment(2L);

        // then
        Assertions.assertThat(findComment.getPosting().getContent()).isEqualTo("제곧내");
        Assertions.assertThat(findComment.getUser().getNickName()).isEqualTo("공돌이");
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() {
        // given
        Board board = new Board("비밀 게시판");
        boardService.createBoard(board);

        User user = new User("김재준", "이메일11", "123", "공돌이11", UserRank.NORMAL);
        userService.join(user);

        Posting posting = new Posting(board, user, "야식 추천좀", "", "제곧내", PostingType.FREE);
        postingService.createPosting(posting);

        User commenter = new User("댓쓴이", "e234", "123", "닉넴234", UserRank.NORMAL);
        userService.join(commenter);

        Comment comment = new Comment(posting, commenter, "치킨이 가장 맛있어요!");
        commentService.addComment(comment);

        // then
        Comment deletedComment = commentService.getOneComment(comment.getId());
        Assertions.assertThat(deletedComment.getContent()).isEqualTo("치킨이 가장 맛있어요!");
        commentService.deleteComment(deletedComment.getId(), userService.findUserByNickName("닉넴234").getId());

        em.flush();
        em.clear();

        Comment oneComment = commentService.getOneComment(1L);
        Assertions.assertThat(oneComment).isNull();
    }

    @Test
    @DisplayName("댓글 작성자가 아닌데 댓글 삭제하려는 상황")
    void deleteCommentByNotProperUser() {
        // given
        Board board = new Board("비밀 게시판");
        boardService.createBoard(board);

        User user = new User("김재준", "이메일1122", "123", "공돌이1122", UserRank.NORMAL);
        userService.join(user);

        Posting posting = new Posting(board, user, "야식 추천좀", "", "제곧내", PostingType.FREE);
        postingService.createPosting(posting);

        User commenter = new User("댓쓴이", "e234567", "123", "닉넴234567", UserRank.NORMAL);
        userService.join(commenter);

        Comment comment = new Comment(posting, commenter, "치킨이 가장 맛있어요!");
        commentService.addComment(comment);

        Comment deletedComment = commentService.getOneComment(comment.getId());
        Assertions.assertThat(deletedComment.getContent()).isEqualTo("치킨이 가장 맛있어요!");

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> commentService.deleteComment(deletedComment.getId(), userService.findUserByNickName("공돌이").getId()));

        Assertions.assertThat(e.getMessage()).isEqualTo("부적절한 사용자 입니다");

    }

}