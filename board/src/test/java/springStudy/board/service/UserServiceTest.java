package springStudy.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.UserRank;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private EntityManager em;

    @BeforeEach
    void createUser() {
        User user = new User("김재준", "이메일", "123", "공돌이", UserRank.NORMAL);
        userService.join(user);
    }

    @Test
    @DisplayName("회원가입")
    void join() {
        User findUser = userService.findUserByNickName("공돌이");

        Assertions.assertThat(findUser.getNickName()).isEqualTo("공돌이");
        Assertions.assertThat(findUser.getName()).isEqualTo("김재준");
        Assertions.assertThat(findUser.getUserRank()).isEqualTo(UserRank.NORMAL);
    }

    @Test
    @DisplayName("중복 Email 회원 가입 - Exception")
    void duplicatedEmailUserJoin() {
        // given
        User dupUser = new User("김재준", "이메일", "123", "공돌이1", UserRank.NORMAL);

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> userService.join(dupUser));

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("중복된 E-mail입니다");
    }

    @Test
    @DisplayName("중복 nickName 회원 가입 - Exception")
    void duplicatedNickNameUserJoin() {
        // given
        User dupUser = new User("김재준", "이메일1", "123", "공돌이", UserRank.NORMAL);

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> userService.join(dupUser));

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("중복된 닉네임 입니다");
    }

    @Test
    @DisplayName("사용자 삭제")
    void deleteUserInfo() {
        // given
        User findUser = userService.findUserByNickName("공돌이");

        // when
        userService.deleteUser(findUser);

        // then
        List<User> users = userService.findAll();
        Assertions.assertThat(users.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("사용자 정보 업데이트")
    void updateUserInfo() {
        // given
        User findUser = userService.findUserByNickName("공돌이");

        // when
        findUser.updateUserInfo("김재준", "바뀐이메일", "바뀐pw", "바뀐닉네임");

        em.flush();
        em.clear();

        // then
        User findUpdatedUser = userService.findUserByNickName("바뀐닉네임");
        Assertions.assertThat(findUpdatedUser.getName()).isEqualTo("김재준");
        Assertions.assertThat(findUpdatedUser.getEmail()).isEqualTo("바뀐이메일");
        Assertions.assertThat(findUpdatedUser.getPassword()).isEqualTo("바뀐pw");
        Assertions.assertThat(findUpdatedUser.getNickName()).isEqualTo("바뀐닉네임");
    }

    @Test
    @DisplayName("사용자의 권한을 관리자로 변경")
    void changeRank() {
        User admin = new User("김재준", "rr@n.com", "123", "ADMIN", UserRank.ADMIN);
        userService.join(admin);

        // given
        User changedRankUser = userService.findUserByNickName("공돌이");

        // when
        userService.changeUserRank(admin.getUserRank(), changedRankUser);

        // then
        Assertions.assertThat(changedRankUser.getUserRank()).isEqualTo(UserRank.ADMIN);
    }
}