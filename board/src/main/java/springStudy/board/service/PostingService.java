package springStudy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springStudy.board.domain.Posting;
import springStudy.board.repository.PostingRepository;
import springStudy.board.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostingService {
    private final UserRepository userRepository;
    private final PostingRepository postingRepository;

    @Transactional
    public void createPosting(Posting posting) {
        postingRepository.save(posting);
    }

    @Transactional
    public void deletePosting(Posting posting, Long userId) {
        if (Objects.equals(posting.getUser().getId(), userId)) {
            postingRepository.remove(posting.getId());
        }
    }

    public List<Posting> getUserPostings(Long userId) {
        return postingRepository.findByUser(userId);
    }

    public List<Posting> getBoardPostings(Long boardId) {
        return postingRepository.findBoardPostings(boardId);
    }
}
