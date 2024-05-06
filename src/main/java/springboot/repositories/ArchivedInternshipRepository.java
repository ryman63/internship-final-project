package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.models.ArchivedInternship;

@Repository
public interface ArchivedInternshipRepository extends JpaRepository<ArchivedInternship, Long> {
}
