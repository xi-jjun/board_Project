package springStudy.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "board")
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name")
    private String name;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "board")
    private List<Posting> postings = new ArrayList<>();

    @Column(name = "board_created_dt")
    private LocalDateTime createDate;

    public Board(String name) {
        this.name = name;
        this.active = true;
        this.createDate = LocalDateTime.now();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeActive() {
        active = !active;
    }
}
