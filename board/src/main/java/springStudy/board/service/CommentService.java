package springStudy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Comment;
import springStudy.board.domain.Posting;
import springStudy.board.repository.CommentRepository;
import springStudy.board.repository.PostingRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getPostingComments(Long postingId) {
        return commentRepository.getCommentsOnPosting(postingId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.remove(commentId);
    }
}
