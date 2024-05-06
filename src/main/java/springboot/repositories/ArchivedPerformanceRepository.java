package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.models.ArchivedPerformance;

@Repository
public interface ArchivedPerformanceRepository extends JpaRepository<ArchivedPerformance, Long> {
}
