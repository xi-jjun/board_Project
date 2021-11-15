package springStudy.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import springStudy.board.domain.enums.PostingType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "posting")
@Entity
@Getter
@RequiredArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posting_id")
    private Long id;

    @ManyToOne(targetEntity = Board.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "posting")
    private List<Comment> comments = new ArrayList<>();

    @Column(length = 30)
    private String title;

    @Column(length = 50)
    private String subtitle;

    @Column
    private String content;

    @Column(name = "posting_type")
    @Enumerated(EnumType.STRING)
    private PostingType postingType;

    @Column(name = "post_created_dt")
    private LocalDateTime createDate;

    @Column(name = "post_updated_dt")
    private LocalDateTime updateDate;

    @Builder
    public Posting(Board board, User user, String title,
                   String subtitle, String content, PostingType postingType) {
        this.board = board;
        this.user = user;
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.postingType = postingType;
        this.createDate = LocalDateTime.now();
    }

    public void updatePosting(String title, String subtitle, String content, PostingType postingType) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.postingType = postingType;
        this.updateDate = LocalDateTime.now();
    }
}