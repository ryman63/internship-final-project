package springboot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ArchivedInternship {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    Internship internship;
}
