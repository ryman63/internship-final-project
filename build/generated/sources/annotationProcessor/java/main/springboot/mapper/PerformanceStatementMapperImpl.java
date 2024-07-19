package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.StatementElement;
import springboot.entities.ParticipantEntity;
import springboot.entities.PerformanceEntity;
import springboot.entities.TaskEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:54+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class PerformanceStatementMapperImpl implements PerformanceStatementMapper {

    @Override
    public StatementElement toStatementElement(PerformanceEntity performance) {
        if ( performance == null ) {
            return null;
        }

        StatementElement statementElement = new StatementElement();

        statementElement.setUsername( performanceParticipantUsername( performance ) );
        statementElement.setTaskName( performanceTaskName( performance ) );
        statementElement.setStatus( performance.getStatus() );

        return statementElement;
    }

    private String performanceParticipantUsername(PerformanceEntity performanceEntity) {
        ParticipantEntity participant = performanceEntity.getParticipant();
        if ( participant == null ) {
            return null;
        }
        return participant.getUsername();
    }

    private String performanceTaskName(PerformanceEntity performanceEntity) {
        TaskEntity task = performanceEntity.getTask();
        if ( task == null ) {
            return null;
        }
        return task.getName();
    }
}
