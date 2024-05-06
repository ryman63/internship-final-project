package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.models.ArchivedParticipant;

@Repository
public interface ArchivedParticipantRepository extends JpaRepository<ArchivedParticipant, Long> {
}
