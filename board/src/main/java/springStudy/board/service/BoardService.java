package springStudy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Board;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.BoardType;
import springStudy.board.repository.BoardRepository;
import springStudy.board.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createContent(Board board) {
        boardRepository.save(board);
        return board.getIdx();
    }

    @Transactional
    public Long updateContent(Long boardId, Board updatedBoard) {
        Board findContents = boardRepository.findById(boardId);
        findContents.updateBoard(updatedBoard.getTitle(),
                updatedBoard.getSubTitle(),
                updatedBoard.getContent(),
                updatedBoard.getBoardType());
        return boardId;
    }

    @Transactional
    public void removeContent(Long boardId) {
        Optional<Board> findBoard = Optional.ofNullable(boardRepository.findById(boardId));
        if (findBoard.isEmpty()) {
            throw new IllegalStateException("Contents is not existed");
        }
        boardRepository.remove(boardId);
    }

    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public Board findOne(Long id) {
        return boardRepository.findById(id);
    }

    public List<Board> findBoardsByNickName(String nickName) {
        Optional<User> findUser = Optional.ofNullable(userRepository.findByNickName(nickName));
        if (findUser.isEmpty()) {
            throw new IllegalStateException("User is not existed");
        }
        return boardRepository.findAllContentsByUser(nickName);
    }
}
