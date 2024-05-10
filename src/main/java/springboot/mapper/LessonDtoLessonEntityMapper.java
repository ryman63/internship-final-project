package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.dto.InternshipDto;
import springboot.dto.LessonDto;
import springboot.entities.InternshipEntity;
import springboot.entities.LessonEntity;
import springboot.services.LessonService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LessonDtoLessonEntityMapper {
    LessonDtoLessonEntityMapper MAPPER = Mappers.getMapper(LessonDtoLessonEntityMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "publicationDateTime", target = "publicationDateTime")
    @Mapping(source = "status", target = "status")
    LessonEntity toLessonEntity(LessonDto LessonDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "publicationDateTime", target = "publicationDateTime")
    @Mapping(source = "status", target = "status")
    LessonDto toLessonDto(LessonEntity LessonEntity);
}
