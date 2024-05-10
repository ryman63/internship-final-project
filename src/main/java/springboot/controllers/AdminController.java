package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.dto.*;
import springboot.mapper.InternshipArchivedInternshipMapper;
import springboot.mapper.ParticipantArchivedParticipantMapper;
import springboot.mapper.PerformanceDtoPerformanceEntityMapper;
import springboot.mapper.PerformanceArchivedPerformanceMapper;
import springboot.entities.*;
import springboot.services.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/admins")
@AllArgsConstructor
public class AdminController {
    private InternshipService internshipService;
    private ParticipantService participantService;
    private TaskService taskService;
    private PerformanceService performanceService;
    private ArchivedService archivedService;
    private GitlabService gitlabService;
    private UserService userService;
    LessonService lessonService;
    ForkService forkService;
    EmailService emailService;


    /**Публикует занятие по ID - возвращает кол-во созданных форков*/
    @ApiOperation(value = "Публикует занятие по ID")
    @PostMapping("/publication/{lessonId}")
    public ResponseEntity<?> publication(@PathVariable Long lessonId) {
        try {
            int counterForks = lessonService.lessonPublication(lessonId);

            return ResponseEntity.ok(new PublicationResponseDto(counterForks));
        } catch (Exception e) {
            // в случае ошибки отправляем сообщение всем админам на почту
            emailService.sendMessageAllAdmins("Ошибка", "Ошибка создания форков проекта");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**Проверка занятий - выдаёт список свежих коммитов занятия*/
    @ApiOperation(value = "Проверка занятий - выдаёт список свежих коммитов занятия")
    @GetMapping("/checkTasks/{lessonId}")
    public ResponseEntity<?> checkTasksLesson(@PathVariable Long lessonId) {
        try {

            return ResponseEntity.ok(taskService.getCheckTasksByLesson(lessonId));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }


    /**Подтверждает участие в стажировке - принимает ID стажировки и ID участника*/
    @ApiOperation(value = "Подписаться на участие в стажировке - принимает ID стажировки и объект участника")
    @PostMapping("/signup/{internshipId}/{participantId}")
    public ResponseEntity<?> signUpForInternship(@PathVariable Long internshipId, @PathVariable Long participantId) {
        try {

            UserEntity createUser = userService.createUserByParticipant(participantId);

            Long createUserId = gitlabService.createUser(participantId);

            // обновляем данные в базе
            participantService.signUpParticipantForInternship(participantId, internshipId, createUserId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Переносит стажировку в архив
     */
    @ApiOperation(value = "Переносит стажировку в архив")
    @DeleteMapping("/archive/{internshipId}")
    public ResponseEntity<?> archiveInternship(@PathVariable Long internshipId) {
        try {
            // архивируем стажировку с последующим удалением репозиториев и юзеров
            archivedService.archiveInternship(internshipId);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    /**
     * Выставляет оценку заданию по ID задания и ID участника
     */
    @ApiOperation(value = "Выставляет оценку заданию по ID задания и ID участника")
    @PostMapping("/rate/{taskId}/{participantId}")
    public ResponseEntity<?> rateParticipantTask(@PathVariable Long taskId, @PathVariable Long participantId, @RequestBody PerformanceDto performanceDto) {
        try {
            PerformanceEntity entity = performanceService.save(performanceDto, taskId, participantId);

            // если комментарий указан, то отправляем комментарий пользователю под последний коммит
            if(!performanceDto.getComment().isEmpty())
                gitlabService.createCommentForCommit(taskId, participantId, new Comment(performanceDto.getComment()));
            return ResponseEntity.ok(entity);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }

    }


    /*** Отчисляет участника со стажировки по Id стажировки и объекту participant*/
    @ApiOperation(value = "Отчисляет участника со стажировки по Id стажировки и Id участника")
    @DeleteMapping("/dropout/{internshipId}/{participantId}")
    public ResponseEntity<?> dropOut(@PathVariable Long internshipId, @PathVariable Long participantId) {
        try {
            archivedService.archiveParticipant(internshipId, participantId);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    /**Возвращает ведомость в которой указаны все опубликованные задачи*/
    @ApiOperation(value = "Возвращает ведомость в которой указаны все опубликованные задачи")
    @GetMapping("/statement/{internshipId}")
    public ResponseEntity<?> getStatement(@PathVariable Long internshipId) {
        try{
            InternshipEntity internshipEntity = internshipService.getInternshipById(internshipId);

            List<StatementElement> statement = performanceService.getStatement(internshipId);

            return ResponseEntity.ok(statement);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
}
