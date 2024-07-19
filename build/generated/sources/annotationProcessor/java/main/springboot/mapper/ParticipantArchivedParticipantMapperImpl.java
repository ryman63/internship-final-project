package springboot.mapper;

import javax.annotation.Generated;
import springboot.entities.ArchivedInternshipEntity;
import springboot.entities.ArchivedParticipantEntity;
import springboot.entities.InternshipEntity;
import springboot.entities.ParticipantEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:55+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class ParticipantArchivedParticipantMapperImpl implements ParticipantArchivedParticipantMapper {

    @Override
    public ArchivedParticipantEntity toArchivedParticipant(ParticipantEntity participantEntity) {
        if ( participantEntity == null ) {
            return null;
        }

        ArchivedParticipantEntity archivedParticipantEntity = new ArchivedParticipantEntity();

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
        archivedParticipantEntity.setId( participantEntity.getId() );
        archivedParticipantEntity.setInternship( internshipEntityToArchivedInternshipEntity( participantEntity.getInternship() ) );

        return archivedParticipantEntity;
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
}
