package springboot.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "archived_participant")
public class ArchivedParticipantEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "archived_internship_id", nullable = false)
    private ArchivedInternshipEntity internship;
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
}
