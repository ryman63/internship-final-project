package springboot.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.entities.ParticipantEntity;
import springboot.entities.PerformanceEntity;
import springboot.entities.TaskEntity;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long> {
    @Query("select perf from PerformanceEntity perf where perf.participant = ?1")
    public List<PerformanceEntity> getAllByParticipant(ParticipantEntity participantEntity);

    @Query("select case when COUNT(perf) > 0 then 'true' else 'false' end from PerformanceEntity perf where " +
            "perf.participant.username = ?1 and " +
            "perf.task.id = ?2 and " +
            "perf.status = 'не зачтено'")
    public boolean checkCountPerformanceByTaskIdAndUsername(String username, Long taskId);

    @Query("select perf from PerformanceEntity perf where perf.task = ?1 and perf.participant = ?2 order by perf.writeDate desc")
    public List<PerformanceEntity> findTopByTaskPublicAndParticipant(TaskEntity taskEntity, ParticipantEntity participantEntity, Pageable pageable);
}
