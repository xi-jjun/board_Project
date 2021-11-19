package springStudy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.UserRank;
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

    public User findUserByNickName(String nickName) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getNickName().equals(nickName))
                return user;
        }
        return null;
    }

    public User findUser(Long userId) {
        return userRepository.findOne(userId);
    }

    @Transactional
    public void updateUser(User updatedUser, String userName, String email, String password, String nickName) {
        checkDuplicatedUserEmail(email);
        checkDuplicatedUserNickName(nickName);
        updatedUser.updateUserInfo(userName, email, password, nickName);
    }

    @Transactional
    public void changeUserRank(UserRank changerRank, User toBeChangedUser) {
        if (changerRank == UserRank.NORMAL) {
            throw new IllegalStateException("관리자 권한이 없습니다");
        }
        toBeChangedUser.changeUserRank(
                toBeChangedUser.getUserRank() == UserRank.NORMAL ? UserRank.ADMIN : UserRank.NORMAL);
    }

    @Transactional
    public void deleteUser(User user) {
        User findUser = userRepository.findOne(user.getId());
        userRepository.remove(findUser.getId());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
