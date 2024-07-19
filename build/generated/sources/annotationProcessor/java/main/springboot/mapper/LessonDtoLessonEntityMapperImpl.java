package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.LessonDto;
import springboot.entities.LessonEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:55+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class LessonDtoLessonEntityMapperImpl implements LessonDtoLessonEntityMapper {

    @Override
    public LessonEntity toLessonEntity(LessonDto LessonDto) {
        if ( LessonDto == null ) {
            return null;
        }

        LessonEntity lessonEntity = new LessonEntity();

        lessonEntity.setName( LessonDto.getName() );
        lessonEntity.setPublicationDateTime( LessonDto.getPublicationDateTime() );
        lessonEntity.setStatus( LessonDto.getStatus() );

        return lessonEntity;
    }

    @Override
    public LessonDto toLessonDto(LessonEntity LessonEntity) {
        if ( LessonEntity == null ) {
            return null;
        }

        LessonDto lessonDto = new LessonDto();

        lessonDto.setName( LessonEntity.getName() );
        lessonDto.setPublicationDateTime( LessonEntity.getPublicationDateTime() );
        lessonDto.setStatus( LessonEntity.getStatus() );

        return lessonDto;
    }
}
