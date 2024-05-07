package springboot.controllers;

import org.springframework.web.bind.annotation.*;
import springboot.models.Internship;
import springboot.models.Participant;

@RestController
@RequestMapping("api/user")
public class UserController {
    @GetMapping("/profile")
    public String userProfile() {
        return "Добро пожаловать в ваш профиль!";
    }


}
