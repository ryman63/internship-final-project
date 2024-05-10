package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.InternshipDto;
import springboot.dto.TaskDto;
import springboot.entities.InternshipEntity;
import springboot.entities.TaskEntity;
import springboot.services.TaskService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskDtoTaskEntityMapper {

    TaskDtoTaskEntityMapper MAPPER = Mappers.getMapper(TaskDtoTaskEntityMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    TaskEntity toTaskEntity(TaskDto taskDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    TaskDto toTaskDto(TaskEntity taskEntity);
}
