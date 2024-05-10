package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.dto.CheckTask;
import springboot.dto.LessonDto;
import springboot.dto.PublicationResponseDto;
import springboot.entities.*;
import springboot.services.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    UserService userService;
    ParticipantService participantService;
    InternshipService internshipService;
    LessonService lessonService;
    PerformanceService performanceService;

    /**
     * Добавляет занятие
     */
    @ApiOperation(value = "Добавляет занятие")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/internship/{internshipId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@PathVariable Long internshipId, @RequestBody LessonDto requestObject) {
        return ResponseEntity.ok(lessonService.createWithInternship(requestObject, internshipId));
    }


    /**
     * Обновляет занятие
     */
    @ApiOperation(value = "Добавляет занятие")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{lessonId}")
    public ResponseEntity<?> update(@PathVariable Long lessonId, @RequestBody LessonDto lessonDto) {
        LessonEntity lessonEntity = lessonService.update(lessonDto, lessonId);
        return ResponseEntity.ok(lessonEntity);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<?> delete(@PathVariable Long lessonId) {
        lessonService.delete(lessonId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<?> getLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.getLessonById(lessonId));
    }
}
