package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.GitlabUser;
import springboot.entities.ParticipantEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:54+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class ParticipantEntityGitlabUserMapperImpl implements ParticipantEntityGitlabUserMapper {

    @Override
    public GitlabUser toGitLabUser(ParticipantEntity participantEntity) {
        if ( participantEntity == null ) {
            return null;
        }

        GitlabUser gitlabUser = new GitlabUser();

        gitlabUser.setName( participantEntity.getFullName() );
        gitlabUser.setEmail( participantEntity.getEmail() );
        gitlabUser.setUsername( participantEntity.getUsername() );
        gitlabUser.setBio( participantEntity.getAboutMe() );
        gitlabUser.setLocation( participantEntity.getCity() );

        return gitlabUser;
    }
}
