package springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot.dto.PerformanceDto;
import springboot.dto.StatementElement;
import springboot.mapper.PerformanceDtoPerformanceEntityMapper;
import springboot.mapper.PerformanceStatementMapper;
import springboot.entities.InternshipEntity;
import springboot.entities.ParticipantEntity;
import springboot.entities.PerformanceEntity;
import springboot.entities.TaskEntity;
import springboot.repositories.InternshipRepository;
import springboot.repositories.ParticipantRepository;
import springboot.repositories.PerformanceRepository;
import springboot.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PerformanceService {
    final PerformanceRepository performanceRepository;
    final TaskRepository taskRepository;
    final ParticipantRepository participantRepository;
    final InternshipRepository internshipRepository;

    public List<PerformanceEntity> getPerformancesByParticipant(ParticipantEntity participantEntity) {
        if (participantEntity == null) return new ArrayList<>();
        return performanceRepository.getAllByParticipant(participantEntity);
    }

    public void removePerformance(PerformanceEntity performanceEntity) {
        performanceRepository.delete(performanceEntity);
    }

    public boolean checkCountPerformance(String username, Long taskId) {
        return performanceRepository.checkCountPerformanceByTaskIdAndUsername(username, taskId);
    }

    public PerformanceEntity save(PerformanceDto performanceDto, Long taskId, Long participantId) {
        TaskEntity taskEntity = taskRepository.getById(taskId);
        ParticipantEntity participantEntity = participantRepository.getById(participantId);
        PerformanceEntity performanceEntity = PerformanceDtoPerformanceEntityMapper.MAPPER.toPerformance(performanceDto);
        performanceEntity.setParticipant(participantEntity);
        performanceEntity.setTask(taskEntity);
        performanceEntity.setWriteDate(LocalDateTime.now());
        return performanceRepository.save(performanceEntity);
    }

    // возвращает ведомость в виде списка объектов (название задачи, username пользователя, оценка)
    public List<StatementElement> getStatement(Long internshipId) throws Exception {

        try {
            List<StatementElement> statement = new ArrayList<>();

            InternshipEntity internshipEntity = internshipRepository.getById(internshipId);

            List<TaskEntity> taskEntityList = taskRepository.getAllPublicatedByInternship(internshipEntity);

            for (TaskEntity taskEntity : taskEntityList) {
                List<ParticipantEntity> participantEntityList = participantRepository.getAllByInternship(internshipEntity);

                for (ParticipantEntity participantEntity : participantEntityList) {

                    List<PerformanceEntity> list = performanceRepository.findTopByTaskPublicAndParticipant(taskEntity, participantEntity, PageRequest.of(0, 1));
                    PerformanceEntity performanceEntity = null;

                    if(list != null && !list.isEmpty())
                         performanceEntity = list.get(0);

                    if(performanceEntity != null)
                        statement.add(PerformanceStatementMapper.MAPPER.toStatementElement(performanceEntity));
                }
            }

            return statement;
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }

    }

}
