package springboot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateForkDto {
    String name;
    String namespace_path;
    String path;
}
