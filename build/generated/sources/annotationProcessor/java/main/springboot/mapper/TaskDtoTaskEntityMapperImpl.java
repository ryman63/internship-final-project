package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.TaskDto;
import springboot.entities.TaskEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:54+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class TaskDtoTaskEntityMapperImpl implements TaskDtoTaskEntityMapper {

    @Override
    public TaskEntity toTaskEntity(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setName( taskDto.getName() );
        taskEntity.setStatus( taskDto.getStatus() );

        return taskEntity;
    }

    @Override
    public TaskDto toTaskDto(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskDto taskDto = new TaskDto();

        taskDto.setName( taskEntity.getName() );
        taskDto.setStatus( taskEntity.getStatus() );

        return taskDto;
    }
}
