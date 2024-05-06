package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.models.ArchivedParticipant;
import springboot.models.ArchivedPerformance;
import springboot.models.Participant;
import springboot.models.Performance;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceArchivedPerformanceMapper {
    PerformanceArchivedPerformanceMapper MAPPER = Mappers.getMapper(PerformanceArchivedPerformanceMapper.class);

    @Mapping(source = "grade", target = "grade")
    ArchivedPerformance toArchivedPerformance(Performance performance);
}
