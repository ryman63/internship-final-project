package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springboot.models.*;
import springboot.services.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    @Autowired
    UserService userService;
    @Autowired
    ParticipantService participantService;
    @Autowired
    InternshipService internshipService;
    @Autowired
    LessonService lessonService;
    @Autowired
    ForkService forkService;
    @Autowired
    GitlabService gitlabService;

    /**Добавляет занятие*/
    @ApiOperation(value = "Добавляет занятие")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/internship/{internshipId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@PathVariable Long internshipId, @RequestBody Lesson requestObject) {
        requestObject.setInternship(internshipService.getInternshipById(internshipId));
        lessonService.add(requestObject);
        return "Lesson successfully added";
    }

    /**Публикует занятие по ID*/
    @ApiOperation(value = "Публикует занятие по ID")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/publication/{id}")
    public ResponseEntity<?> publication(@PathVariable Long id) {
        int counterForks = 0;
        try {
            Lesson lesson = lessonService.getLessonById(id);
            if (lesson == null)
                throw new Exception("Lesson not found");
            List<Task> taskList = lesson.getTasks();
            if (taskList == null)
                throw new Exception("Tasks not found");

            // получение всех активных участников
            List<Participant> activeParticipants = participantService.GetParticipantsByInternship(lesson.getInternship());

            for (Task task : taskList) {
                for (Participant participant : activeParticipants) {
                    counterForks++;

                    Long createForkId = gitlabService.createFork(task, participant.getUsername());

                    // создаём объект fork и заполняем
                    Fork fork = new Fork();
                    fork.setLesson(lesson);
                    fork.setTask(task);
                    fork.setUser(userService.getUserByName(participant.getUsername()));

                    fork.setGitLabRepositoryId(String.valueOf(createForkId));
                    // записываем в базу
                    forkService.add(fork);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.ok("Lesson successfully publication, created " + counterForks + " forks");
    }

    /**Проверка занятий - выдаёт список свежих коммитов занятия*/
    @ApiOperation(value = "Проверка занятий - выдаёт список свежих коммитов занятия")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/checkTasks/{lessonId}")
    public ResponseEntity<?> checkTasksLesson(@PathVariable Long lessonId) {

        List<CheckTask> checkTasks = new ArrayList<>();

        try {
            Lesson lesson = lessonService.getLessonById(lessonId);
            if (lesson == null)
                throw new NullPointerException("Lesson not found");

            List<Fork> forks = forkService.getAllForksByLesson(lesson);

            for (Fork fork : forks) {
                // получаем последние коммиты всех форков связанных с текущим заданием
                CheckTask checkTask = gitlabService.getLastForkCommit(fork);

                if(checkTask != null)
                    checkTasks.add(checkTask);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(checkTasks);
    }

}
