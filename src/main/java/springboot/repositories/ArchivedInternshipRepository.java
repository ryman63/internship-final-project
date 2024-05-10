package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entities.ArchivedInternshipEntity;

@Repository
public interface ArchivedInternshipRepository extends JpaRepository<ArchivedInternshipEntity, Long> {
}
