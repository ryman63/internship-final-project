package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springboot.mapper.ParticipantGitlabUserMapper;
import springboot.models.*;
import springboot.services.GitlabService;
import springboot.services.InternshipService;
import springboot.services.ParticipantService;
import springboot.services.UserService;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    GitlabService gitlabService;

    @Autowired
    InternshipService internshipService;
    @Autowired
    UserService userService;

    /**Получить участника стажировки - возвращает участника по ID*/
    @ApiOperation(value = "Получить участника стажировки - возвращает участника по ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipant(@PathVariable Long id) {
        Participant model = participantService.getParticipantById(id);

        if (model == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Participant not found.");
        }

        return ResponseEntity.ok(model);
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

    /**Подписаться на участие в стажировке - принимает ID стажировки и объект участника*/
    @ApiOperation(value = "Подписаться на участие в стажировке - принимает ID стажировки и объект участника")
    @PostMapping("/signup/{internshipId}")
    public ResponseEntity<?> signUpForInternship(@PathVariable Long internshipId, @RequestBody Participant participant) {
        Internship internship = internshipService.getInternshipById(internshipId);
        if (internship == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Проверка доступности записи на стажировку
            if (internship.getDateEndRecording().isBefore(LocalDate.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration for this internship is closed.");
            }

            // проверка на существующего участника
            if (participantService.checkExistsParticipant(participant))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Participant exists.");

            // создание нового пользователя с ролью User
            User newUser = new User();
            newUser.setUsername(participant.getUsername());
            newUser.setEnabled(true);
            newUser.setPassword("user");

            Authority userAuthority = new Authority();
            userAuthority.setUser(newUser);
            userAuthority.setAuthority("ROLE_USER");

            GitlabUser gitlabUser = ParticipantGitlabUserMapper.MAPPER.toGitLabUser(participant);

            Long createUserId = gitlabService.createUser(gitlabUser);

            participant.setGitlabId(String.valueOf(createUserId));

            // записываем в базу
            participantService.signUpParticipantForInternship(internship, participant);
            userService.addUser(newUser, userAuthority);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user: " + e.getMessage());
        }

        return ResponseEntity.ok("Participant signed up successfully.");
    }
}
