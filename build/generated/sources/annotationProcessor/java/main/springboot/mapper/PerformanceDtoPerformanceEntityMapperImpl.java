package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.PerformanceDto;
import springboot.entities.PerformanceEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:55+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class PerformanceDtoPerformanceEntityMapperImpl implements PerformanceDtoPerformanceEntityMapper {

    @Override
    public PerformanceEntity toPerformance(PerformanceDto participant) {
        if ( participant == null ) {
            return null;
        }

        PerformanceEntity performanceEntity = new PerformanceEntity();

        performanceEntity.setStatus( participant.getStatus() );
        performanceEntity.setComment( participant.getComment() );

        return performanceEntity;
    }
}
