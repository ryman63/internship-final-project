package springboot.dto;

import lombok.Data;

@Data
public class TaskResponseDto {
    private Long id;
    private String name;
    private String gitLabRepositoryId;
    private String status;
}
