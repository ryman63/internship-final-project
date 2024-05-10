import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.dto.CheckTask;
import springboot.dto.TaskDto;
import springboot.entities.ForkEntity;
import springboot.entities.InternshipEntity;
import springboot.entities.LessonEntity;
import springboot.entities.TaskEntity;
import springboot.mapper.TaskDtoTaskEntityMapper;
import springboot.repositories.InternshipRepository;
import springboot.repositories.LessonRepository;
import springboot.repositories.TaskRepository;
import springboot.services.ForkService;
import springboot.services.GitlabService;
import springboot.services.PerformanceService;
import springboot.services.TaskService;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private InternshipRepository internshipRepository;


    @Mock
    private GitlabService gitlabService;

    @Mock
    private PerformanceService performanceService;

    @Mock
    private ForkService forkService;
    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private TaskDtoTaskEntityMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    public TaskServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllByInternship() {
        // Arrange
        Long internshipId = 1L;
        List<TaskEntity> taskEntities = new ArrayList<>();

        // Act
        when(internshipRepository.getById(internshipId)).thenReturn(new InternshipEntity());
        when(taskRepository.getAllByInternship(any())).thenReturn(taskEntities);

        // Assert
        List<TaskEntity> retrievedTaskEntities = taskService.getAllByInternship(internshipId);
        verify(internshipRepository, times(1)).getById(internshipId);
        verify(taskRepository, times(1)).getAllByInternship(any());
        assert(retrievedTaskEntities != null);
    }

    @Test
    public void testGetTaskById() {
        // Arrange
        Long taskId = 1L;
        TaskEntity taskEntity = new TaskEntity();

        // Act
        when(taskRepository.getById(taskId)).thenReturn(taskEntity);

        // Assert
        TaskEntity retrievedTaskEntity = taskService.getTaskById(taskId);
        verify(taskRepository, times(1)).getById(taskId);
        assert(retrievedTaskEntity != null);
    }

    @Test
    public void testUpdate() {
        // Arrange
        Long taskId = 1L;
        TaskDto taskDto = new TaskDto();
        TaskEntity taskEntity = new TaskEntity();

        // Act
        when(taskRepository.getById(taskId)).thenReturn(taskEntity);
        when(taskRepository.save(taskEntity)).thenReturn(taskEntity);

        // Assert
        TaskEntity updatedTaskEntity = taskService.update(taskDto, taskId);
        verify(taskRepository, times(1)).getById(taskId);
        verify(taskRepository, times(1)).save(taskEntity);
        assert(updatedTaskEntity != null);
    }

    @Test
    public void testDelete() {
        // Arrange
        Long taskId = 1L;
        TaskEntity taskEntity = new TaskEntity();
        // добавьте необходимые mock-объекты и настройки

        // Act
        when(taskRepository.getById(taskId)).thenReturn(taskEntity);
        taskService.delete(taskId);

        // Assert
        verify(taskRepository, times(1)).delete(taskEntity);
    }
}
