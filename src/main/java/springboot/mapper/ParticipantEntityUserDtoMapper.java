package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.ParticipantDto;
import springboot.dto.UserDto;
import springboot.entities.ParticipantEntity;
import springboot.services.UserService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParticipantEntityUserDtoMapper {
    ParticipantEntityUserDtoMapper MAPPER = Mappers.getMapper(ParticipantEntityUserDtoMapper.class);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    UserDto toUserDto(ParticipantEntity participantEntity);

}
