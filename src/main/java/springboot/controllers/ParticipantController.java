package springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springboot.mapper.ParticipantGitlabUserMapper;
import springboot.models.GitlabUser;
import springboot.models.Internship;
import springboot.models.Participant;
import springboot.services.InternshipService;
import springboot.services.ParticipantService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

    @Value("${gitlab.apiUrl}")
    private String gitLabApiUrl;

    @Value("${gitlab.token}")
    private String gitLabToken;

    @Autowired
    ParticipantService participantService;

    @Autowired
    InternshipService internshipService;

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


            // создание пользователя в gitlab
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(gitLabToken);
            headers.set("Content-Type", "application/json");

            String userUrl = gitLabApiUrl + "/users";

            try{
                GitlabUser gitlabUser = ParticipantGitlabUserMapper.MAPPER.toGitLabUser(participant);
                HttpEntity<GitlabUser> requestEntity = new HttpEntity<>(gitlabUser, headers);

                // Создайте RestTemplate
                RestTemplate restTemplate = new RestTemplate();

                // Отправляем запрос с помощью exchange
                ResponseEntity<GitlabUser> response = restTemplate.exchange(
                        userUrl, // URL API
                        HttpMethod.POST, // Метод запроса
                        requestEntity, // Тело запроса и заголовки
                        GitlabUser.class); // Ожидаемый тип ответа
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user: " + e.getMessage());
            }

            // делаем изменения в базе данных
            participantService.signUpParticipantForInternship(internship, participant);
            return ResponseEntity.ok("Participant signed up successfully.");

        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
