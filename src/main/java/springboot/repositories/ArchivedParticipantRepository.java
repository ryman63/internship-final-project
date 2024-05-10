package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entities.ArchivedParticipantEntity;

@Repository
public interface ArchivedParticipantRepository extends JpaRepository<ArchivedParticipantEntity, Long> {
}
