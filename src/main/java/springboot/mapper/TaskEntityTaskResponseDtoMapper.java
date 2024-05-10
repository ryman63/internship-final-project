package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.TaskDto;
import springboot.dto.TaskResponseDto;
import springboot.entities.TaskEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskEntityTaskResponseDtoMapper {
    TaskEntityTaskResponseDtoMapper MAPPER = Mappers.getMapper(TaskEntityTaskResponseDtoMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "gitLabRepositoryId", target = "gitLabRepositoryId")
    @Mapping(source = "id", target = "id")
    TaskResponseDto toTaskResponseDto(TaskEntity taskEntity);
}
