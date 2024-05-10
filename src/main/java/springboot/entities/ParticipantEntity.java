package springboot.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "participant")
@Data
public class ParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    private InternshipEntity internship;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String username;
    private String telegramId;
    private String aboutMe;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private String city;
    private String educationStatus;
    private String university;
    private String faculty;
    private String specialty;
    private int course;
    private String gitlabId;
}
