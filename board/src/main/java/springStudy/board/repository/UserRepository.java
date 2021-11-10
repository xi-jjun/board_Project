package springStudy.board.repository;

import springStudy.board.domain.User;

import java.util.List;

public interface UserRepository {
    void save(User user);

    User findOne(Long id);

    List<User> findByName(String name);

    User findByNickName(String nickName);
}
