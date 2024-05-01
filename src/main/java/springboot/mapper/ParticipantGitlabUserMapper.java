package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.models.GitlabUser;
import springboot.models.Participant;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParticipantGitlabUserMapper {

    ParticipantGitlabUserMapper MAPPER = Mappers.getMapper(ParticipantGitlabUserMapper.class);

    @Mapping(source = "fullName", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "aboutMe", target = "bio")
    @Mapping(source = "city", target = "location")
    GitlabUser toGitLabUser(Participant participant);
}
