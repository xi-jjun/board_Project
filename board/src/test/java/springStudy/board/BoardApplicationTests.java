package springStudy.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Board;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.BoardType;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class BoardApplicationTests {
	@Autowired
	private EntityManager em;

	@Test
	void insert() {
		User user = new User("김재준", "공대생은 공돌공돌해", "pw1234", "rlawowns97@gmail.com", LocalDateTime.now());
		em.persist(user); // IDENTITY로 설정하여 이 때에 INSERT query 가 날라가기 때문에 아래 find 에서 1L을 써도 인식을 할 수 있는 것이다.
		Board board = new Board("제목11", "부제목22", "내용이야ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ", BoardType.free, user);
		em.persist(board);

		User findUser = em.find(User.class, 1L);
		Assertions.assertThat(user).isEqualTo(findUser);
	}

}
