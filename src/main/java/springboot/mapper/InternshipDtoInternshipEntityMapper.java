package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import springboot.dto.InternshipDto;
import springboot.entities.ArchivedInternshipEntity;
import springboot.entities.InternshipEntity;
import springboot.services.InternshipService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InternshipDtoInternshipEntityMapper {

    InternshipDtoInternshipEntityMapper MAPPER = Mappers.getMapper(InternshipDtoInternshipEntityMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "dateBegin", target = "dateBegin")
    @Mapping(source = "dateEnd", target = "dateEnd")
    @Mapping(source = "state", target = "state")
    InternshipEntity toInternshipEntity(InternshipDto internshipDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "dateBegin", target = "dateBegin")
    @Mapping(source = "dateEnd", target = "dateEnd")
    @Mapping(source = "state", target = "state")
    InternshipDto toInternshipDto(InternshipEntity internshipEntity);
}
