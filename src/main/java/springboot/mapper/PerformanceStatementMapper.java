package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.StatementElement;
import springboot.entities.PerformanceEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceStatementMapper {
    PerformanceStatementMapper MAPPER = Mappers.getMapper(PerformanceStatementMapper.class);

    @Mapping(source = "performance.participant.username", target = "username")
    @Mapping(source = "performance.task.name", target = "taskName")
    @Mapping(source = "performance.status", target = "status")
    StatementElement toStatementElement(PerformanceEntity performance);
}
