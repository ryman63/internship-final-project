package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.models.Participant;
import springboot.models.Performance;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    @Query("select perf from Performance perf where perf.participant = ?1")
    public List<Performance> getAllByParticipant(Participant participant);
}
