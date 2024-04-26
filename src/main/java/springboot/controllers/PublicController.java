package springboot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public")
public class PublicController {
    @GetMapping("/hello")
    public String publicHello() {
        return "Привет из публичной части API!";
    }
}
