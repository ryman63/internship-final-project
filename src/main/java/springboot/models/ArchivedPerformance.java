package springboot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ArchivedPerformance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    Performance performance;
}
