package springStudy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.User;
import springStudy.board.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void join(User user) {
        checkDuplicatedUserEmail(user.getEmail());
        checkDuplicatedUserNickName(user.getNickName());
        userRepository.save(user);
    }

    private void checkDuplicatedUserEmail(String email) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                throw new IllegalStateException("중복된 E-mail입니다");
            }
        }
    }

    private void checkDuplicatedUserNickName(String nickName) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getNickName().equals(nickName)) {
                throw new IllegalStateException("중복된 닉네임 입니다");
            }
        }
    }

    @Transactional
    public void updateUser(User updatedInfo) {
        checkDuplicatedUserEmail(updatedInfo.getEmail());
        checkDuplicatedUserNickName(updatedInfo.getNickName());
        userRepository.save(updatedInfo);
    }

    @Transactional
    public void deleteUser(User user) {
        User findUser = userRepository.findOne(user.getId());
        userRepository.remove(findUser.getId());
    }
}
