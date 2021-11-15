package springStudy.board.domain;

import lombok.Getter;
import springStudy.board.domain.enums.PostingType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "posting")
@Entity
@Getter
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
}