package springboot.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springboot.models.Lesson;
import springboot.models.Task;
import springboot.services.GitlabService;
import springboot.services.LessonService;
import springboot.services.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    GitlabService gitlabService;

    @Autowired
    TaskService taskService;
    @Autowired
    LessonService lessonService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{lessonId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addTask(@PathVariable Long lessonId, @RequestBody Task requestObject) {

        try {

            Lesson lesson = lessonService.getLessonById(lessonId);

            if(lesson == null)
                throw new NullPointerException("lesson equals null");
            requestObject.setLesson(lesson);

            Long createProjectId = gitlabService.createProject(requestObject);

            // получаем id созданного проекта и записываем в наш объект
            requestObject.setGitLabRepositoryId(String.valueOf(createProjectId));

            taskService.add(requestObject);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Task successfully added in Lesson");
    }
}
