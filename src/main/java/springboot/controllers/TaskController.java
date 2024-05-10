package springboot.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.dto.TaskDto;
import springboot.dto.TaskResponseDto;
import springboot.entities.TaskEntity;
import springboot.mapper.TaskEntityTaskResponseDtoMapper;
import springboot.services.GitlabService;
import springboot.services.LessonService;
import springboot.services.TaskService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    GitlabService gitlabService;
    TaskService taskService;
    LessonService lessonService;

    /**
     * Создание задания - принимает ID занятия
     */
    @ApiOperation(value = "Создание задания - принимает ID занятия и объект задания")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{lessonId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addTask(@PathVariable Long lessonId, @RequestBody TaskDto requestObject) {
        try {

            // создаём проект в гитлаб и получаем его id
            Long createProjectId = gitlabService.createProject(requestObject);

            // сохраняем новый объект task с lesson и id проекта гитлаб
            TaskEntity entity = taskService.saveWithLessonAndRepositoryId(requestObject, lessonId, String.valueOf(createProjectId));

            TaskResponseDto taskResponseDto = TaskEntityTaskResponseDtoMapper.MAPPER.toTaskResponseDto(entity);

            return ResponseEntity.ok(taskResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Обновляет задание по ID - принимает объект задания
     */
    @ApiOperation(value = "Обновляет задание по ID - принимает объект задания")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{taskId}")
    public ResponseEntity<?> update(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.update(taskDto, taskId));
    }

    /**
     * Удаляет задание по ID
     */
    @ApiOperation(value = "Удаляет задание по ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
