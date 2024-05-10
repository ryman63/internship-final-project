package springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.entities.InternshipEntity;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class LessonDto {
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime publicationDateTime;
    private String status;
}
