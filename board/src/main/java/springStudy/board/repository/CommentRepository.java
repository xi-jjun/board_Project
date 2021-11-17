package springStudy.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springStudy.board.domain.Comment;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }

    public void remove(Long id) {
        Comment findComment = em.find(Comment.class, id);
        em.remove(findComment);
    }

    public List<Comment> findByUser(Long userId) {
        return em.createQuery("select c from Comment c where c.user.id = :userId", Comment.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Comment> getCommentsOnPosting(Long postingId) {
        return em.createQuery("select c from Comment c where c.posting.id = :postingId", Comment.class)
                .setParameter("postingId", postingId)
                .getResultList();
    }
}
