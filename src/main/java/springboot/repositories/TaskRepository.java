package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.entities.InternshipEntity;
import springboot.entities.TaskEntity;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("select tasks from TaskEntity tasks where tasks.lesson.internship = ?1")
    public List<TaskEntity> getAllByInternship(InternshipEntity internshipEntity);

    @Query("select tasks from TaskEntity tasks where tasks.lesson.internship = ?1 and tasks.lesson.status='опубликовано'")
    public List<TaskEntity> getAllPublicatedByInternship(InternshipEntity internshipEntity);
}
