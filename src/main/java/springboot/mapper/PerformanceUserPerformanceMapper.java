package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.UserPerformance;
import springboot.entities.PerformanceEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceUserPerformanceMapper {
    PerformanceUserPerformanceMapper MAPPER = Mappers.getMapper(PerformanceUserPerformanceMapper.class);

    @Mapping(source = "performance.status", target = "status")
    @Mapping(source = "performance.task.name", target = "taskName")
    @Mapping(source = "writeDate", target = "writeDate")
    UserPerformance toUserPerformance(PerformanceEntity performance);
}
