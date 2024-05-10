import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.dto.PerformanceDto;
import springboot.dto.StatementElement;
import springboot.entities.*;
import springboot.mapper.PerformanceStatementMapper;
import springboot.repositories.InternshipRepository;
import springboot.repositories.ParticipantRepository;
import springboot.repositories.PerformanceRepository;
import springboot.repositories.TaskRepository;
import springboot.services.PerformanceService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PerformanceServiceTest {

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private InternshipRepository internshipRepository;

    @InjectMocks
    private PerformanceService performanceService;

    public PerformanceServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPerformancesByParticipant() {
        ParticipantEntity participantEntity = new ParticipantEntity();
        when(performanceRepository.getAllByParticipant(participantEntity)).thenReturn(new ArrayList<>());

        List<PerformanceEntity> performances = performanceService.getPerformancesByParticipant(participantEntity);

        assert(performances != null);
        verify(performanceRepository, times(1)).getAllByParticipant(participantEntity);
    }

    @Test
    public void testRemovePerformance() {
        PerformanceEntity performanceEntity = new PerformanceEntity();

        performanceService.removePerformance(performanceEntity);

        verify(performanceRepository, times(1)).delete(performanceEntity);
    }

    @Test
    public void testCheckCountPerformance() {
        String username = "testuser";
        Long taskId = 1L;
        when(performanceRepository.checkCountPerformanceByTaskIdAndUsername(username, taskId)).thenReturn(true);

        boolean result = performanceService.checkCountPerformance(username, taskId);

        assert(result);
        verify(performanceRepository, times(1)).checkCountPerformanceByTaskIdAndUsername(username, taskId);
    }

    @Test
    void save_ShouldSavePerformance_WhenValidInput() {
        PerformanceDto performanceDto = new PerformanceDto();
        Long taskId = 1L;
        Long participantId = 2L;

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskId);

        ParticipantEntity participantEntity = new ParticipantEntity();
        participantEntity.setId(participantId);

        PerformanceEntity performanceEntity = new PerformanceEntity();
        performanceEntity.setId(1L);
        performanceEntity.setTask(taskEntity);
        performanceEntity.setParticipant(participantEntity);
        performanceEntity.setWriteDate(LocalDateTime.now());

        when(taskRepository.getById(taskId)).thenReturn(taskEntity);
        when(participantRepository.getById(participantId)).thenReturn(participantEntity);
        when(performanceRepository.save(any(PerformanceEntity.class))).thenReturn(performanceEntity);

        PerformanceEntity savedPerformance = performanceService.save(performanceDto, taskId, participantId);

        assertNotNull(savedPerformance);
        assertEquals(taskEntity, savedPerformance.getTask());
        assertEquals(participantEntity, savedPerformance.getParticipant());
        assertNotNull(savedPerformance.getWriteDate());
        verify(taskRepository, times(1)).getById(taskId);
        verify(participantRepository, times(1)).getById(participantId);
        verify(performanceRepository, times(1)).save(any(PerformanceEntity.class));
    }

}
