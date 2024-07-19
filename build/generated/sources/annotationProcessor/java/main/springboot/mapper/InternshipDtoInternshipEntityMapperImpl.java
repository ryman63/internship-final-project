package springboot.mapper;

import javax.annotation.Generated;
import springboot.dto.InternshipDto;
import springboot.entities.InternshipEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:54+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class InternshipDtoInternshipEntityMapperImpl implements InternshipDtoInternshipEntityMapper {

    @Override
    public InternshipEntity toInternshipEntity(InternshipDto internshipDto) {
        if ( internshipDto == null ) {
            return null;
        }

        InternshipEntity internshipEntity = new InternshipEntity();

        internshipEntity.setName( internshipDto.getName() );
        internshipEntity.setDescription( internshipDto.getDescription() );
        internshipEntity.setDateBegin( internshipDto.getDateBegin() );
        internshipEntity.setDateEnd( internshipDto.getDateEnd() );
        internshipEntity.setState( internshipDto.getState() );
        internshipEntity.setDateEndRecording( internshipDto.getDateEndRecording() );

        return internshipEntity;
    }

    @Override
    public InternshipDto toInternshipDto(InternshipEntity internshipEntity) {
        if ( internshipEntity == null ) {
            return null;
        }

        InternshipDto internshipDto = new InternshipDto();

        internshipDto.setName( internshipEntity.getName() );
        internshipDto.setDescription( internshipEntity.getDescription() );
        internshipDto.setDateBegin( internshipEntity.getDateBegin() );
        internshipDto.setDateEnd( internshipEntity.getDateEnd() );
        internshipDto.setState( internshipEntity.getState() );
        internshipDto.setDateEndRecording( internshipEntity.getDateEndRecording() );

        return internshipDto;
    }
}
