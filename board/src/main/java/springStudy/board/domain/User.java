package springStudy.board.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    @Column
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "user_created_dt")
    private LocalDateTime regDate;

    @Column(name = "user_updated_dt")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "posting_id")
    private List<Posting> postings = new ArrayList<>();

    @OneToMany(mappedBy = "comment_id")
    private List<Comment> comments = new ArrayList<>();
}
