package springboot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ArchivedParticipant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    Participant participant;
}
