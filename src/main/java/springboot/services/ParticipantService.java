package springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.dto.ParticipantDto;
import springboot.dto.ParticipantUpdateDto;
import springboot.entities.InternshipEntity;
import springboot.entities.ParticipantEntity;
import springboot.mapper.ParticipantDtoParticipantEntityMapper;
import springboot.mapper.ParticipantEntityUserDtoMapper;
import springboot.repositories.ParticipantRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ParticipantService {

    final ParticipantRepository participantRepository;
    final InternshipService internshipService;

    public ParticipantEntity save(ParticipantDto participantDto) throws Exception {

        ParticipantEntity participantEntity = ParticipantDtoParticipantEntityMapper.MAPPER.toParticipantEntity(participantDto);

        // проверка на существующего участника
        if (checkExistsParticipant(participantEntity))
            throw new Exception("Participant exists");

        return participantRepository.save(participantEntity);
    }

    public ParticipantEntity getParticipantById(Long id) {
        return participantRepository.getById(id);
    }

    public void signUpParticipantForInternship(Long participantId, Long internshipId, Long gitlabUserId) throws Exception {

        ParticipantEntity participantEntity = participantRepository.getById(participantId);

        InternshipEntity internshipEntity =  internshipService.getInternshipById(internshipId);
        if (internshipEntity == null) {
            throw new Exception("internship not found");
        }
        // Проверка доступности записи на стажировку
        if (internshipEntity.getDateEndRecording().isBefore(LocalDate.now())) {
            throw new Exception("Registration for this internship is closed");
        }

        participantEntity.setInternship(internshipEntity);
        participantEntity.setGitlabId(String.valueOf(gitlabUserId));

        // обновляем данные
        participantRepository.save(participantEntity);
    }

    public boolean checkExistsParticipant(ParticipantEntity participantEntity)
    {
        // проверяем участника на соответствие уже имеющимся
        return participantRepository.existByUsernameEmailTelegramIdMobileNumber
                (participantEntity.getUsername(),
                participantEntity.getEmail(),
                participantEntity.getTelegramId(),
                participantEntity.getMobileNumber());
    }

    public List<ParticipantEntity> getParticipantsByInternship(InternshipEntity internshipEntity){
        return participantRepository.getAllByInternship(internshipEntity);
    }

    public void removeParticipant(ParticipantEntity participantEntity){
        participantRepository.delete(participantEntity);
    }

    public ParticipantEntity getParticipantByUsername(String username) {
        if(username.isEmpty()) return null;
            return participantRepository.getParticipantByUsername(username);
    }

    public ParticipantEntity update(ParticipantUpdateDto participantDto, Long participantId) {
        ParticipantEntity participantEntity = participantRepository.getById(participantId);
        participantEntity.setCity(participantDto.getCity());
        participantEntity.setFaculty(participantDto.getFaculty());
        participantEntity.setAboutMe(participantDto.getAboutMe());
        participantEntity.setDateOfBirth(participantDto.getDateOfBirth());
        participantEntity.setEducationStatus(participantDto.getEducationStatus());
        participantEntity.setUniversity(participantDto.getUniversity());
        participantEntity.setSpecialty(participantDto.getSpecialty());
        participantEntity.setCourse(participantDto.getCourse());
        return participantRepository.save(participantEntity);
    }

    public void delete(Long participantId) {
        participantRepository.delete(participantRepository.getById(participantId));
    }
}
