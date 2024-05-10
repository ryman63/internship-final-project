package springboot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectGitlabDto {
    String name;
    boolean initialize_with_readme;
}
