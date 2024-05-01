package springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springboot.models.Lesson;
import springboot.models.Participant;
import springboot.models.Task;
import springboot.services.LessonService;
import springboot.services.ParticipantService;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Value("${gitlab.apiUrl}")
    private String gitLabApiUrl;

    @Value("${gitlab.token}")
    private String gitLabToken;

    @Autowired
    ParticipantService participantService;

    @Autowired
    LessonService lessonService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@RequestBody Lesson requestObject) {
        lessonService.add(requestObject);
        return "Lesson successfully added";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/publication/{id}")
    public ResponseEntity<?> publication(@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        if(lesson == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lesson not found");
        List<Task> taskList = lesson.getTasks();
        if(taskList == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tasks not found");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gitLabToken);

        String url = gitLabApiUrl + "/projects";

        // Создание тела запроса
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");

        // получение всех активных участников
        List<Participant> activeParticipants = participantService.GetParticipantsByInternshipId(lesson.getInternship().getId());



        // для каждого задания из занятия
        for(Task task : taskList)
        {
            // для каждого активного участника
            for(Participant participant : activeParticipants)
            {
                // создаём форк таски
                HttpEntity<String> requestEntity = new HttpEntity<>(
                        "{\"name\": \"" + task.getGitLabRepositoryId() + "\","
                                + "\"\nnamespace_path\": \"" + participant.getUsername() + "\"}", requestHeaders);

                // Отправка запроса
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(requestHeaders), String.class);
                if(response.getStatusCode() != HttpStatus.CREATED)
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fork not created");
            }
        }

        return ResponseEntity.ok("Lesson successfully publication");
    }
}
