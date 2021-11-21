package springStudy.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springStudy.board.domain.User;
import springStudy.board.domain.enums.UserRank;
import springStudy.board.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String createUser(@Valid UserForm form, BindingResult result) {
        if (result.hasErrors()) {
            log.info("is error?");
            return "users/createUserForm";
        }
        UserRank rank;
        log.info(form.getUserRank() + " " +form.getName());
        if(form.getUserRank().equals("ADMIN")) rank = UserRank.ADMIN;
        else rank = UserRank.NORMAL;

        User user = new User(form.getName(),
                form.getEmail(),
                form.getPassword(),
                form.getNickName(),
                rank);
        userService.join(user);

        return "redirect:/";
    }
}
