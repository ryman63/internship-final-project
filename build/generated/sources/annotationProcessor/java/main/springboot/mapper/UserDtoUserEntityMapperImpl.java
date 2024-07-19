package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.UserDto;
import springboot.entities.UserEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:55+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class UserDtoUserEntityMapperImpl implements UserDtoUserEntityMapper {

    @Override
    public UserEntity toUserEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername( userDto.getUsername() );
        userEntity.setPassword( userDto.getPassword() );
        userEntity.setEmail( userDto.getEmail() );

        return userEntity;
    }
}
