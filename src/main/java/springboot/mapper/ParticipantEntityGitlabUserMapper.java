package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.GitlabUser;
import springboot.entities.ParticipantEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParticipantEntityGitlabUserMapper {

    ParticipantEntityGitlabUserMapper MAPPER = Mappers.getMapper(ParticipantEntityGitlabUserMapper.class);

    @Mapping(source = "fullName", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "aboutMe", target = "bio")
    @Mapping(source = "city", target = "location")
    GitlabUser toGitLabUser(ParticipantEntity participantEntity);
}
