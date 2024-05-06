package springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.models.Internship;
import springboot.models.Participant;
import springboot.repositories.InternshipRepository;
import springboot.repositories.ParticipantRepository;

@Service
public class InternshipService {
    @Autowired
    final InternshipRepository repository;
    public InternshipService(InternshipRepository internshipRepository) {
        this.repository = internshipRepository;
    }

    public void addInternship(Internship internship){
        repository.save(internship);
    }

    public Internship getInternshipById(Long id) {
        return repository.getById(id);
    }

    public void removeInternship(Internship internship){
        repository.delete(internship);
    }

}
