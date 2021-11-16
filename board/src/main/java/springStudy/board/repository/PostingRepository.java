package springStudy.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springStudy.board.domain.Posting;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostingRepository {
    private final EntityManager em;

    public void save(Posting posting) {
        em.persist(posting);
    }

    public void remove(Long id) {
        Posting findPosting = em.find(Posting.class, id);
        em.remove(findPosting);
    }

    public List<Posting> findBoardPostings(Long boardId) {
        return em.createQuery("select p from Posting p where p.board.id = :boardId", Posting.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public List<Posting> findByUser(Long userId) {
        return em.createQuery("select p from Posting p where p.user.id = :userId", Posting.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
