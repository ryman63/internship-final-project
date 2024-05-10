package springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.dto.InternshipDto;
import springboot.entities.InternshipEntity;
import springboot.mapper.InternshipDtoInternshipEntityMapper;
import springboot.repositories.InternshipRepository;

@Service
@AllArgsConstructor
public class InternshipService {
    final InternshipRepository repository;
    public InternshipEntity save(InternshipDto internshipDto){
        return repository.save(InternshipDtoInternshipEntityMapper.MAPPER.toInternshipEntity(internshipDto));
    }

    public InternshipEntity update(InternshipDto internshipDto, Long internshipId) {
        InternshipEntity internshipEntity = repository.getById(internshipId);
        internshipEntity.setName(internshipDto.getName());
        internshipEntity.setDescription(internshipDto.getDescription());
        internshipEntity.setState(internshipDto.getState());
        internshipEntity.setDateEnd(internshipDto.getDateEnd());
        internshipEntity.setDateBegin(internshipDto.getDateBegin());
        internshipEntity.setDateEndRecording(internshipDto.getDateEndRecording());
        return repository.save(internshipEntity);
    }

    public InternshipEntity getInternshipById(Long id) {
        return repository.getById(id);
    }

    public void removeInternship(InternshipEntity internshipEntity){
        repository.delete(internshipEntity);
    }

}
