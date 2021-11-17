package springStudy.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springStudy.board.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public void remove(Long id) {
        User findUser = em.find(User.class, id);
        em.remove(findUser);
    }
}
