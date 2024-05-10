package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.entities.InternshipEntity;

@Repository
public interface InternshipRepository extends JpaRepository<InternshipEntity, Long> {

}
