package springStudy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Board;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.UserRank;
import springStudy.board.repository.BoardRepository;
import springStudy.board.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createBoard(Board board) {
        checkDuplicatedBoardName(board);
        boardRepository.save(board);
    }

    private void checkDuplicatedBoardName(Board board) {
        List<Board> boards = boardRepository.findAllBoards();
        for (Board b : boards) {
            if(b.getName().equals(board.getName())) throw new IllegalStateException("중복된 이름의 게시판입니다");
        }
    }

    @Transactional
    public void changeActive(Long userId, Long boardId) {
        User user = userRepository.findOne(userId);
        if (user.getUserRank() == UserRank.ADMIN) {
            Board board = boardRepository.findBoard(boardId);
            board.changeActive(!board.isActive());
        }
    }
}
