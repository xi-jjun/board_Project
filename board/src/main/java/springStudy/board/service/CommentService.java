package springStudy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Comment;
import springStudy.board.domain.Posting;
import springStudy.board.domain.User;
import springStudy.board.repository.CommentRepository;
import springStudy.board.repository.PostingRepository;
import springStudy.board.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getPostingComments(Long postingId) {
        return commentRepository.getCommentsOnPosting(postingId);
    }

    public Comment getOneComment(Long commentId) {
        return commentRepository.findOne(commentId);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        checkUserWrite(userId, commentId);
        commentRepository.remove(commentId);
    }

    private void checkUserWrite(Long userId, Long commentId) {
        Comment findComment = commentRepository.findOne(commentId);
        if (!Objects.equals(findComment.getUser().getId(), userId))
            throw new IllegalStateException("부적절한 사용자 입니다");
    }
}
