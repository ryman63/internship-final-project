package springboot.mapper;

import javax.annotation.Generated;
import springboot.entities.ArchivedInternshipEntity;
import springboot.entities.InternshipEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T20:42:55+0400",
    comments = "version: 1.6.0.Beta1, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 1.8.0_402 (Temurin)"
)
public class InternshipArchivedInternshipMapperImpl implements InternshipArchivedInternshipMapper {

    @Override
    public ArchivedInternshipEntity toArchivedInternship(InternshipEntity participant) {
        if ( participant == null ) {
            return null;
        }

        ArchivedInternshipEntity archivedInternshipEntity = new ArchivedInternshipEntity();

        archivedInternshipEntity.setName( participant.getName() );
        archivedInternshipEntity.setDescription( participant.getDescription() );
        archivedInternshipEntity.setDateBegin( participant.getDateBegin() );
        archivedInternshipEntity.setDateEnd( participant.getDateEnd() );
        archivedInternshipEntity.setDateEndRecording( participant.getDateEndRecording() );
        archivedInternshipEntity.setId( participant.getId() );

        return archivedInternshipEntity;
    }
}
