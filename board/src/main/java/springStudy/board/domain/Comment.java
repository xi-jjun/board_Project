package springStudy.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(targetEntity = Posting.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment_content")
    private String content;

    @Column(name = "comment_created_dt")
    private LocalDateTime createDate;

    @Column(name = "comment_updated_dt")
    private LocalDateTime updateDate;

    public Comment(Posting posting, User user, String content) {
        this.posting = posting;
        this.user = user;
        this.content = content;
        this.createDate = LocalDateTime.now();
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
