package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.PerformanceDto;
import springboot.entities.PerformanceEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceDtoPerformanceEntityMapper {

    PerformanceDtoPerformanceEntityMapper MAPPER = Mappers.getMapper(PerformanceDtoPerformanceEntityMapper.class);

    @Mapping(source = "status", target = "status")
    @Mapping(source = "comment", target = "comment")
    PerformanceEntity toPerformance(PerformanceDto participant);
}
