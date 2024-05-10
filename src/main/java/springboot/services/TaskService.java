package springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.dto.CheckTask;
import springboot.dto.TaskDto;
import springboot.entities.ForkEntity;
import springboot.entities.InternshipEntity;
import springboot.entities.LessonEntity;
import springboot.entities.TaskEntity;
import springboot.mapper.TaskDtoTaskEntityMapper;
import springboot.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    final TaskRepository taskRepository;
    final InternshipRepository internshipRepository;
    final LessonRepository lessonRepository;
    final ForkRepository forkRepository;
    final GitlabService gitlabService;
    final PerformanceRepository performanceRepository;

    public TaskEntity save(TaskDto taskDto) {
        return taskRepository.save(TaskDtoTaskEntityMapper.MAPPER.toTaskEntity(taskDto));
    }

    public TaskEntity saveWithLessonAndRepositoryId(TaskDto taskDto, Long lessonId, String gitlabRepositoryId) {
        LessonEntity lessonEntity = lessonRepository.getById(lessonId);

        if (lessonEntity == null)
            throw new NullPointerException("lesson is null");

        TaskEntity entity = TaskDtoTaskEntityMapper.MAPPER.toTaskEntity(taskDto);

        entity.setGitLabRepositoryId(gitlabRepositoryId);
        entity.setLesson(lessonEntity);
        return taskRepository.save(entity);
    }

    public List<TaskEntity> getAllByInternship(Long internshipId) {
        InternshipEntity entity = internshipRepository.getById(internshipId);
        return taskRepository.getAllByInternship(entity);
    }


    public List<CheckTask> getCheckTasksByLesson(Long lessonId) {


        List<ForkEntity> forks = forkRepository.findAllByLesson(lessonRepository.getById(lessonId));

        List<CheckTask> checkTasks = new ArrayList<>();

        for (ForkEntity fork : forks) {
            // получаем последние коммиты всех форков связанных с текущим заданием
            CheckTask checkTask = gitlabService.getLastForkCommit(fork);

            // проверяем не проверялась ли задача ранее или была уже отправлена на доработку
            if (performanceRepository.checkCountPerformanceByTaskIdAndUsername(fork.getParticipant().getUsername(), fork.getTask().getId())
                    && checkTask != null)
                checkTasks.add(checkTask);
        }

        return checkTasks;
    }

    public TaskEntity getTaskById(Long id) {
        return taskRepository.getById(id);
    }

    public TaskEntity update(TaskDto taskDto, Long taskId) {
        TaskEntity taskEntity = taskRepository.getById(taskId);
        taskEntity.setName(taskDto.getName());
        taskEntity.setStatus(taskDto.getStatus());
        return taskRepository.save(taskEntity);
    }

    public void delete(Long taskId) {
        TaskEntity taskEntity = taskRepository.getById(taskId);
        gitlabService.deleteProjectById(taskEntity.getGitLabRepositoryId());
        taskRepository.delete(taskEntity);
    }
}
