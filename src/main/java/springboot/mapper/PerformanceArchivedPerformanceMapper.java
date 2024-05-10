package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.entities.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceArchivedPerformanceMapper {
    PerformanceArchivedPerformanceMapper MAPPER = Mappers.getMapper(PerformanceArchivedPerformanceMapper.class);

    @Mapping(source = "performance.status", target = "status")
    @Mapping(source = "performance.task.name", target = "taskName")
    ArchivedPerformanceEntity toArchivedPerformance(PerformanceEntity performance);
}
