package springStudy.board.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "board")
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name")
    private String name;

    @OneToMany(mappedBy = "posting_id")
    private List<Posting> postings = new ArrayList<>();

    @Column(name = "board_created_dt")
    private LocalDateTime createDate;
}
