package springboot.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springboot.mapper.InternshipArchivedInternshipMapper;
import springboot.mapper.ParticipantArchivedParticipantMapper;
import springboot.mapper.PerformanceArchivedPerformanceMapper;
import springboot.models.*;
import springboot.services.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private InternshipService internshipService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private PerformanceService performanceService;
    @Autowired
    private ArchivedService archivedService;
    @Autowired
    private UserService userService;
    @Autowired
    private ForkService forkService;
    @Autowired
    private GitlabService gitlabService;

    /**Переносит стажировку в архив*/
    @ApiOperation(value = "Переносит стажировку в архив")
    @GetMapping("/archive/{internshipId}")
    public ResponseEntity<?> archivedInternship(@PathVariable Long internshipId) {
        try {
            Internship internship = internshipService.getInternshipById(internshipId);
            if (internship == null)
                throw new NullPointerException("Internship null");

            // архивируем стажировку
            ArchivedInternship archivedInternship = InternshipArchivedInternshipMapper.MAPPER.toArchivedInternship(internship);
            archivedService.addArchivedInternship(archivedInternship);

            List<Participant> participantList = participantService.GetParticipantsByInternship(internship);
            if (participantList == null)
                throw new NullPointerException("Participants null");

            for (Participant participant : participantList) {
                // архивируем участников стажировки
                ArchivedParticipant archivedParticipant = ParticipantArchivedParticipantMapper.MAPPER.toArchivedParticipant(participant);
                archivedParticipant.setInternship(archivedInternship);
                archivedService.addArchivedParticipant(archivedParticipant);

                List<Performance> performanceList = performanceService.getPerformancesByParticipant(participant);
                if (performanceList != null) {
                    for (Performance performance : performanceList) {
                        // архивируем успеваемость участников
                        ArchivedPerformance archivedPerformance = PerformanceArchivedPerformanceMapper.MAPPER.toArchivedPerformance(performance);
                        archivedPerformance.setTaskName(performance.getTask().getName());
                        archivedPerformance.setParticipant(archivedParticipant);
                        archivedService.addArchivedPerformance(archivedPerformance);

                        // удаляем из базы устаревшую успеваемость участников
                        performanceService.removePerformance(performance);
                    }
                }

                // удаляем из базы бывших участников стажировки
                participantService.removeParticipant(participant);

                // удаляем пользователей из базы
                userService.removeUserByName(participant.getUsername());

                // удаляем пользователей из гитлаба
                gitlabService.deleteUserById(participant.getGitlabId());
            }

            // удаление из базы закончившейся стажировки
            internshipService.removeInternship(internship);

            // удаление форков
            List<Fork> forks = forkService.getAllForksByInternship(internship);
            for (Fork fork : forks) {
                gitlabService.deleteProjectById(fork.getGitLabRepositoryId());
            }

            // удаление эталонных репозиториев
            List<Task> tasks = taskService.getAllByInternship(internship);
            for (Task task : tasks) {
                gitlabService.deleteProjectById(task.getGitLabRepositoryId());
            }

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
        return ResponseEntity.ok("Internship successfully archive");
    }

    /**Выставляет оценку заданию по ID задания и ID участника*/
    @ApiOperation(value = "Выставляет оценку заданию по ID задания и ID участника")
    @PostMapping("/rate/{taskId}/{participantId}")
    public ResponseEntity<?> rateParticipantTask(@PathVariable Long taskId, @PathVariable Long participantId){
        try {
            Task task = taskService.getTaskById(taskId);
            Participant participant = participantService.getParticipantById(participantId);

        } catch (Exception exception) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
        return ResponseEntity.ok("");
    }
}
