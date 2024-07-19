package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.UserDto;
import springboot.entities.ParticipantEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:54+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class ParticipantEntityUserDtoMapperImpl implements ParticipantEntityUserDtoMapper {

    @Override
    public UserDto toUserDto(ParticipantEntity participantEntity) {
        if ( participantEntity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUsername( participantEntity.getUsername() );
        userDto.setEmail( participantEntity.getEmail() );

        return userDto;
    }
}
