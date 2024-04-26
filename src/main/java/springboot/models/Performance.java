package springboot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "performance")
@Data
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    private String grade;
}
