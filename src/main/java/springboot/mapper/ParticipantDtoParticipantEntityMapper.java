package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.ParticipantDto;
import springboot.entities.ArchivedParticipantEntity;
import springboot.entities.ParticipantEntity;
import springboot.services.ParticipantService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParticipantDtoParticipantEntityMapper {
    ParticipantDtoParticipantEntityMapper MAPPER = Mappers.getMapper(ParticipantDtoParticipantEntityMapper.class);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "telegramId", target = "telegramId")
    @Mapping(source = "aboutMe", target = "aboutMe")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "educationStatus", target = "educationStatus")
    @Mapping(source = "university", target = "university")
    @Mapping(source = "faculty", target = "faculty")
    @Mapping(source = "specialty", target = "specialty")
    @Mapping(source = "course", target = "course")
    ParticipantDto toParticipantDto(ParticipantEntity participantEntity);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "telegramId", target = "telegramId")
    @Mapping(source = "aboutMe", target = "aboutMe")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "educationStatus", target = "educationStatus")
    @Mapping(source = "university", target = "university")
    @Mapping(source = "faculty", target = "faculty")
    @Mapping(source = "specialty", target = "specialty")
    @Mapping(source = "course", target = "course")
    ParticipantEntity toParticipantEntity(ParticipantDto participantDto);
}
