package springboot.services;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import springboot.models.Internship;
import springboot.models.Participant;
import springboot.repositories.ParticipantRepository;

@Service
public class ParticipantService {

    final ParticipantRepository repository;

    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    public void addParticipant(Participant participant){
        repository.save(participant);
    }

    public Participant getParticipantById(Long id) {
        return repository.getById(id);
    }

    public void signUpParticipantForInternship(Internship internship, Participant participant){
        participant.setInternship(internship);
        repository.save(participant);
    }

    public boolean checkExistsParticipant(Participant participant)
    {
        return repository.existByUsernameEmailTelegramIdMobileNumber
                (participant.getUsername(),
                participant.getEmail(),
                participant.getTelegramId(),
                participant.getMobileNumber());
    }

}
