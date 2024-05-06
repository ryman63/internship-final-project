package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.models.Fork;
import springboot.models.Internship;
import springboot.models.Lesson;

import java.util.List;

@Repository
public interface ForkRepository extends JpaRepository<Fork, Long> {
    @Query("select forks from Fork forks where forks.lesson = ?1")
    public List<Fork> findAllByLesson(Lesson lesson);
    @Modifying
    @Query("delete Fork fork where fork.lesson.internship = ?1")
    public void removeAllByInternship(Internship internship);
    @Query("select forks from Fork forks where forks.lesson.internship = ?1")
    public List<Fork> findAllByInternship(Internship internship);
}
