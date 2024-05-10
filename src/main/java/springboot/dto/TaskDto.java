package springboot.dto;

import lombok.Data;
import springboot.entities.LessonEntity;

@Data
public class TaskDto {
    private String name;
    private String status;
}
