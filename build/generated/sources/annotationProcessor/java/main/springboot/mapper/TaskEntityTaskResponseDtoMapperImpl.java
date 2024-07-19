package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.TaskResponseDto;
import springboot.entities.TaskEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:54+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class TaskEntityTaskResponseDtoMapperImpl implements TaskEntityTaskResponseDtoMapper {

    @Override
    public TaskResponseDto toTaskResponseDto(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskResponseDto taskResponseDto = new TaskResponseDto();

        taskResponseDto.setName( taskEntity.getName() );
        taskResponseDto.setStatus( taskEntity.getStatus() );
        taskResponseDto.setGitLabRepositoryId( taskEntity.getGitLabRepositoryId() );
        taskResponseDto.setId( taskEntity.getId() );

        return taskResponseDto;
    }
}
