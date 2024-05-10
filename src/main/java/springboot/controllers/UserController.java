package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springboot.dto.UserPerformance;
import springboot.mapper.PerformanceUserPerformanceMapper;
import springboot.entities.ParticipantEntity;
import springboot.entities.PerformanceEntity;
import springboot.services.ParticipantService;
import springboot.services.PerformanceService;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {
    PerformanceService performanceService;
    ParticipantService participantService;

    /**
     * Возвращает список оценок авторизированного пользователя
     */
    @ApiOperation(value = "Возвращает список оценок авторизированного пользователя")
    @GetMapping("/check/performance")
    public ResponseEntity<?> checkPerformance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ParticipantEntity participantEntity = participantService.getParticipantByUsername(auth.getName());
        List<PerformanceEntity> performanceEntityList = performanceService.getPerformancesByParticipant(participantEntity);
        List<UserPerformance> userPerformances = new ArrayList<>();

        for (PerformanceEntity performanceEntity : performanceEntityList)
            userPerformances.add(PerformanceUserPerformanceMapper.MAPPER.toUserPerformance(performanceEntity));

        return ResponseEntity.ok(userPerformances);
    }
}
