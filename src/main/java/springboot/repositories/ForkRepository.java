package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.entities.*;

import java.util.List;

@Repository
public interface ForkRepository extends JpaRepository<ForkEntity, Long> {
    @Query("select forks from ForkEntity forks where forks.lesson = ?1")
    public List<ForkEntity> findAllByLesson(LessonEntity lessonEntity);
    @Modifying
    @Query("delete ForkEntity fork where fork.lesson.internship = ?1")
    public void removeAllByInternship(InternshipEntity internshipEntity);
    @Query("select forks from ForkEntity forks where forks.lesson.internship = ?1")
    public List<ForkEntity> findAllByInternship(InternshipEntity internshipEntity);

    @Query("select fork from ForkEntity fork where fork.task.id = ?1 and fork.participant.id = ?2")
    public ForkEntity getForkByTaskAndParticipant(Long taskId, Long participantId);
}
