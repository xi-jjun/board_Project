package springStudy.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @RequiredArgsConstructor
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

    @OneToMany(mappedBy = "user")
    private List<Posting> postings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public User(String name, String email, String password, String nickName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.regDate = LocalDateTime.now();
    }

    public void updateUserInfo(String name, String email, String password, String nickName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.updateDate = LocalDateTime.now();
    }
}

