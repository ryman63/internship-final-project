package springboot.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "task")
@Data
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lesson;

    private String name;
    private String gitLabRepositoryId;
    private String status;
}