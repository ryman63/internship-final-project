package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.UserPerformance;
import springboot.entities.PerformanceEntity;
import springboot.entities.TaskEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:54+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class PerformanceUserPerformanceMapperImpl implements PerformanceUserPerformanceMapper {

    @Override
    public UserPerformance toUserPerformance(PerformanceEntity performance) {
        if ( performance == null ) {
            return null;
        }

        UserPerformance userPerformance = new UserPerformance();

        userPerformance.setStatus( performance.getStatus() );
        userPerformance.setTaskName( performanceTaskName( performance ) );
        userPerformance.setWriteDate( performance.getWriteDate() );

        return userPerformance;
    }

    private String performanceTaskName(PerformanceEntity performanceEntity) {
        TaskEntity task = performanceEntity.getTask();
        if ( task == null ) {
            return null;
        }
        return task.getName();
    }
}
