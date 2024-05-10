package springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ParticipantDto {
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
