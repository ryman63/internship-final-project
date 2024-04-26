package springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.models.Internship;
import springboot.services.InternshipService;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Добро пожаловать в административную панель!";
    }
}
