package springboot.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
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

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskComment> adminComment = new ArrayList<>();
}