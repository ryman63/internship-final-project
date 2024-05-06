package springboot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Fork {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gitLabRepositoryId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name= "task_id", nullable = false)
    private Task task;
}
