package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.UserDto;
import springboot.entities.UserEntity;
import springboot.services.UserService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoUserEntityMapper {
    UserDtoUserEntityMapper MAPPER = Mappers.getMapper(UserDtoUserEntityMapper.class);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    UserEntity toUserEntity(UserDto userDto);
}
