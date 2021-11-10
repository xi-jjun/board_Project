package springStudy.board.repository;

import springStudy.board.domain.Board;
import springStudy.board.domain.User;

import java.util.List;

public interface BoardRepository {
    void save(Board board);

    Board findById(Long id);

    List<Board> findAllContentsByUser(String nickName);

    void remove(Long id);

    List<Board> findAll();
}
