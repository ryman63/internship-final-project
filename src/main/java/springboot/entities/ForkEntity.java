package springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fork")
public class ForkEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gitLabRepositoryId;
    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private ParticipantEntity participant;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lesson;

    @ManyToOne
    @JoinColumn(name= "task_id", nullable = false)
    private TaskEntity task;
}
