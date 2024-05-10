package springboot.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "archived_performance")
public class ArchivedPerformanceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "archived_participant_id", nullable = false)
    private ArchivedParticipantEntity participant;

    private String taskName;

    private String status;
}
