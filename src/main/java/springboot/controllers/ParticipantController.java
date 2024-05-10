package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.dto.ParticipantDto;
import springboot.dto.ParticipantUpdateDto;
import springboot.entities.*;
import springboot.services.GitlabService;
import springboot.services.InternshipService;
import springboot.services.ParticipantService;
import springboot.services.UserService;

@RestController
@RequestMapping("/api/participants")
@AllArgsConstructor
public class ParticipantController {

    ParticipantService participantService;
    GitlabService gitlabService;
    InternshipService internshipService;
    UserService userService;


    /**Получить участника стажировки - возвращает участника по ID*/
    @ApiOperation(value = "Получить участника стажировки - возвращает участника по ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipant(@PathVariable Long id) {
        ParticipantEntity model = participantService.getParticipantById(id);

        if (model == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Participant not found.");
        }

        return ResponseEntity.ok(model);
    }

    /**Создаёт участника без привязки к стажировке, требуется дальнейшее подтверждение*/
    @ApiOperation(value = "Создаёт участника без привязки к стажировке, требуется дальнейшее подтверждение")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@RequestBody ParticipantDto requestObject) {
        try{
            return ResponseEntity.ok(participantService.save(requestObject));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{participantId}")
    public ResponseEntity<?> update(@PathVariable Long participantId, ParticipantUpdateDto participantUpdateDto) {
        return ResponseEntity.ok(participantService.update(participantUpdateDto, participantId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{participantId}")
    public ResponseEntity<?> delete(@PathVariable Long participantId) {
        participantService.delete(participantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
