package springboot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    private String name;
    private String gitLabRepositoryId;
    private String status;
    private String grade;
    private String adminComment;
}