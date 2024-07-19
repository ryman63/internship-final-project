package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.ParticipantDto;
import springboot.entities.ParticipantEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:54+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class ParticipantDtoParticipantEntityMapperImpl implements ParticipantDtoParticipantEntityMapper {

    @Override
    public ParticipantDto toParticipantDto(ParticipantEntity participantEntity) {
        if ( participantEntity == null ) {
            return null;
        }

        ParticipantDto participantDto = new ParticipantDto();

        participantDto.setFullName( participantEntity.getFullName() );
        participantDto.setEmail( participantEntity.getEmail() );
        participantDto.setMobileNumber( participantEntity.getMobileNumber() );
        participantDto.setUsername( participantEntity.getUsername() );
        participantDto.setTelegramId( participantEntity.getTelegramId() );
        participantDto.setAboutMe( participantEntity.getAboutMe() );
        participantDto.setDateOfBirth( participantEntity.getDateOfBirth() );
        participantDto.setCity( participantEntity.getCity() );
        participantDto.setEducationStatus( participantEntity.getEducationStatus() );
        participantDto.setUniversity( participantEntity.getUniversity() );
        participantDto.setFaculty( participantEntity.getFaculty() );
        participantDto.setSpecialty( participantEntity.getSpecialty() );
        participantDto.setCourse( participantEntity.getCourse() );

        return participantDto;
    }

    @Override
    public ParticipantEntity toParticipantEntity(ParticipantDto participantDto) {
        if ( participantDto == null ) {
            return null;
        }

        ParticipantEntity participantEntity = new ParticipantEntity();

        participantEntity.setFullName( participantDto.getFullName() );
        participantEntity.setEmail( participantDto.getEmail() );
        participantEntity.setMobileNumber( participantDto.getMobileNumber() );
        participantEntity.setUsername( participantDto.getUsername() );
        participantEntity.setTelegramId( participantDto.getTelegramId() );
        participantEntity.setAboutMe( participantDto.getAboutMe() );
        participantEntity.setDateOfBirth( participantDto.getDateOfBirth() );
        participantEntity.setCity( participantDto.getCity() );
        participantEntity.setEducationStatus( participantDto.getEducationStatus() );
        participantEntity.setUniversity( participantDto.getUniversity() );
        participantEntity.setFaculty( participantDto.getFaculty() );
        participantEntity.setSpecialty( participantDto.getSpecialty() );
        participantEntity.setCourse( participantDto.getCourse() );

        return participantEntity;
    }
}
