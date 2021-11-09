package springStudy.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Board;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.BoardType;
import springStudy.board.repository.BoardRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class BoardApplicationTests {
	@Autowired
	private EntityManager em;

	@Autowired private BoardRepository boardRepository;

	@Test
	void insert() { // insert into User (id, name...) values (1L, ...)
		// 새로운 회원가입
		User user = new User("재준", "공돌이", "1234pw",
				"rrl@gmail.com", LocalDateTime.now());

		Board newContent = new Board("제목", "부제목","내용이다ㅏㅏㅏㅏㅏㅏㅏ",
				BoardType.FREE, user);

		boardRepository.save(newContent);

		Board findBoard = boardRepository.findById(newContent.getIdx());
		Assertions.assertThat(findBoard).isEqualTo(newContent);
	}

	@Test
	void findByUserName() {
		User user = new User("UserA1", "공돌이1", "1234pw",
				"rrl@gmail.com", LocalDateTime.now());

		Board newContent = new Board("제목A", "A부제목","A내용이다ㅏㅏㅏㅏㅏㅏㅏ",
				BoardType.FREE, user);

		Board newContent1 = new Board("제1목", "부제목","내용이다ㅏㅏㅏㅏㅏㅏㅏ",
				BoardType.FREE, user);

		Board newContent2 = new Board("제목", "부제목","내용이다ㅏㅏㅏㅏㅏㅏㅏ",
				BoardType.FREE, user);

		em.persist(user);
		boardRepository.save(newContent);
		boardRepository.save(newContent1);
		boardRepository.save(newContent2);

		em.flush();
		em.clear();
		List<Board> thisUserContents = boardRepository.findAllByUser(user);
		for (Board content : thisUserContents) {
			System.out.println("content = " + content);
		}
		Assertions.assertThat(3).isEqualTo(thisUserContents.size());
	}

}
