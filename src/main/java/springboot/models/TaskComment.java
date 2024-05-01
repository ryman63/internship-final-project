package springboot.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class TaskComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime writeDate;
    String author;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    Task task;
}
