package springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import springboot.models.ArchivedInternship;
import springboot.models.Internship;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InternshipArchivedInternshipMapper {
    InternshipArchivedInternshipMapper MAPPER = Mappers.getMapper(InternshipArchivedInternshipMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "dateBegin", target = "dateBegin")
    @Mapping(source = "dateEnd", target = "dateEnd")
    @Mapping(source = "dateEndRecording", target = "dateEndRecording")
    ArchivedInternship toArchivedInternship(Internship participant);
}
