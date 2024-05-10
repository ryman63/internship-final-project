package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.entities.LessonEntity;
import springboot.entities.TaskEntity;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    @Query("select t from TaskEntity t where t.lesson = ?1")
    public List<TaskEntity> getTasks(LessonEntity lesson);
}
