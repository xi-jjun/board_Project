package springStudy.board.repository;

import org.springframework.stereotype.Repository;
import springStudy.board.domain.Board;
import springStudy.board.domain.User;

import java.util.List;

public interface BoardRepository {
    void save(Board board);

    Board findById(Long id);

    List<Board> findAllByUser(User user);
}
