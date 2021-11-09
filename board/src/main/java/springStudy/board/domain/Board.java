package springStudy.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import springStudy.board.domain.enums.BoardType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity // JPA 가 관리하는 Class 라는 것을 명시
@NoArgsConstructor
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long idx;

    @Column // default name : field name
    private String title;

    @Column
    private String subTitle;

    @Column
    private String content;

    @Column @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime updateDate;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Board(String title, String subTitle, String content, BoardType boardType, User user) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.boardType = boardType;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Board{" +
                "idx=" + idx +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", content='" + content + '\'' +
                ", boardType=" + boardType +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", user=" + user +
                '}';
    }
}
