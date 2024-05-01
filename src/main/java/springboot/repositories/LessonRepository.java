package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.models.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
