package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.models.Internship;
import springboot.models.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select tasks from Task tasks where tasks.lesson.internship = ?1")
    public List<Task> getAllByInternship(Internship internship);
}
