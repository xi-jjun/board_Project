package springStudy.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springStudy.board.domain.Board;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public Board findBoard(Long id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAllBoards() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }
}
