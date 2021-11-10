package springStudy.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long idx;

    @Column
    private String nickName;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime updateDate;

//    @OneToMany(mappedBy = "user")
//    private List<Board> boards = new ArrayList<>();

    public User(String name, String nickName, String password, String email) {
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.createDate = LocalDateTime.now();
    }
}
