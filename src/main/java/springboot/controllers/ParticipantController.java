package springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.models.Internship;
import springboot.models.Participant;
import springboot.services.InternshipService;
import springboot.services.ParticipantService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    InternshipService internshipService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public Participant getParticipant(@PathVariable Long id) {
        Participant model = participantService.getParticipantById(id);
        return model;
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//
//    public String add(@RequestBody Participant requestObject) {
//        //logger.info("Recieved internship object: " + requestObject);
//        participantService.addParticipant(requestObject);
//        return "Участник успешно добавлен";
//    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/signup/{internshipId}")
    public ResponseEntity<?> signUpForInternship(@PathVariable Long internshipId, @RequestBody Participant participant){
        Internship internship = internshipService.getInternshipById(internshipId);
        if (internship != null) {
            // Проверка доступности записи на стажировку
            if (internship.getDateEndRecording().isBefore(LocalDate.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration for this internship is closed.");
            }

            // проверка на существующего участника
            if(participantService.checkExistsParticipant(participant))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Participant exists.");

            participantService.signUpParticipantForInternship(internship, participant);
            return ResponseEntity.ok("Participant signed up successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
