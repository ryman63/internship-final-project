package springboot.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import springboot.models.Internship;

import javax.sql.DataSource;
@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {

}
