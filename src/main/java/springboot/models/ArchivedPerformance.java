package springboot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ArchivedPerformance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "archived_participant_id", nullable = false)
    private ArchivedParticipant participant;

    private String taskName;

    private String grade;
}
