package springStudy.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springStudy.board.domain.Board;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
    private final EntityManager em;

    @Override
    public void save(Board board) {
        em.persist(board);
    }

    @Override
    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    @Override
    public List<Board> findAllContentsByUser(String nickName) {
        return em.createQuery("select b from Board b where b.user.nickName=:nickName", Board.class)
                .setParameter("nickName", nickName)
                .getResultList();
    }

    @Override
    public void remove(Long id) {
        Board findBoard = em.find(Board.class, id);
        em.remove(findBoard);
    }

    @Override
    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }
}
