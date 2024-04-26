package springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.models.Internship;
import springboot.models.Participant;
import springboot.services.InternshipService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    @Autowired
    InternshipService internshipService;

    //private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public Internship getInternship(@PathVariable Long id) {
        Internship model = internshipService.getInternshipById(id);
        return model;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public String add(@RequestBody Internship requestObject) {
        //logger.info("Recieved internship object: " + requestObject);
        internshipService.addInternship(requestObject);
        return "Стажировка успешно добавлена";
    }


}
