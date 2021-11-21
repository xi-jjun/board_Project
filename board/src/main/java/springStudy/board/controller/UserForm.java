package springStudy.board.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserForm {
    private String name;
    private String email;
    private String password;
    private String nickName;
    private String userRank;
}
