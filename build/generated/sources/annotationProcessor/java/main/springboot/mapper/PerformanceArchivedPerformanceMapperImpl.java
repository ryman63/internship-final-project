package springboot.mapper;

import javax.annotation.Generated;
import springboot.entities.ArchivedInternshipEntity;
import springboot.entities.ArchivedParticipantEntity;
import springboot.entities.ArchivedPerformanceEntity;
import springboot.entities.InternshipEntity;
import springboot.entities.ParticipantEntity;
import springboot.entities.PerformanceEntity;
import springboot.entities.TaskEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:55+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class PerformanceArchivedPerformanceMapperImpl implements PerformanceArchivedPerformanceMapper {

    @Override
    public ArchivedPerformanceEntity toArchivedPerformance(PerformanceEntity performance) {
        if ( performance == null ) {
            return null;
        }

        ArchivedPerformanceEntity archivedPerformanceEntity = new ArchivedPerformanceEntity();

        archivedPerformanceEntity.setStatus( performance.getStatus() );
        archivedPerformanceEntity.setTaskName( performanceTaskName( performance ) );
        archivedPerformanceEntity.setId( performance.getId() );
        archivedPerformanceEntity.setParticipant( participantEntityToArchivedParticipantEntity( performance.getParticipant() ) );

        return archivedPerformanceEntity;
    }

    private String performanceTaskName(PerformanceEntity performanceEntity) {
        TaskEntity task = performanceEntity.getTask();
        if ( task == null ) {
            return null;
        }
        return task.getName();
    }

    protected ArchivedInternshipEntity internshipEntityToArchivedInternshipEntity(InternshipEntity internshipEntity) {
        if ( internshipEntity == null ) {
            return null;
        }

        ArchivedInternshipEntity archivedInternshipEntity = new ArchivedInternshipEntity();

        archivedInternshipEntity.setId( internshipEntity.getId() );
        archivedInternshipEntity.setName( internshipEntity.getName() );
        archivedInternshipEntity.setDescription( internshipEntity.getDescription() );
        archivedInternshipEntity.setDateBegin( internshipEntity.getDateBegin() );
        archivedInternshipEntity.setDateEnd( internshipEntity.getDateEnd() );
        archivedInternshipEntity.setDateEndRecording( internshipEntity.getDateEndRecording() );

        return archivedInternshipEntity;
    }

    protected ArchivedParticipantEntity participantEntityToArchivedParticipantEntity(ParticipantEntity participantEntity) {
        if ( participantEntity == null ) {
            return null;
        }

        ArchivedParticipantEntity archivedParticipantEntity = new ArchivedParticipantEntity();

        archivedParticipantEntity.setId( participantEntity.getId() );
        archivedParticipantEntity.setInternship( internshipEntityToArchivedInternshipEntity( participantEntity.getInternship() ) );
        archivedParticipantEntity.setFullName( participantEntity.getFullName() );
        archivedParticipantEntity.setEmail( participantEntity.getEmail() );
        archivedParticipantEntity.setMobileNumber( participantEntity.getMobileNumber() );
        archivedParticipantEntity.setUsername( participantEntity.getUsername() );
        archivedParticipantEntity.setTelegramId( participantEntity.getTelegramId() );
        archivedParticipantEntity.setAboutMe( participantEntity.getAboutMe() );
        archivedParticipantEntity.setDateOfBirth( participantEntity.getDateOfBirth() );
        archivedParticipantEntity.setCity( participantEntity.getCity() );
        archivedParticipantEntity.setEducationStatus( participantEntity.getEducationStatus() );
        archivedParticipantEntity.setUniversity( participantEntity.getUniversity() );
        archivedParticipantEntity.setFaculty( participantEntity.getFaculty() );
        archivedParticipantEntity.setSpecialty( participantEntity.getSpecialty() );
        archivedParticipantEntity.setCourse( participantEntity.getCourse() );

        return archivedParticipantEntity;
    }
}
