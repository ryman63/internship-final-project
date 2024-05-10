package springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.entities.*;
import springboot.mapper.InternshipArchivedInternshipMapper;
import springboot.mapper.ParticipantArchivedParticipantMapper;
import springboot.mapper.PerformanceArchivedPerformanceMapper;
import springboot.repositories.*;

import java.util.List;

@Service
@AllArgsConstructor
public class ArchivedService {
    final ArchivedInternshipRepository archivedInternshipRepository;
    final ArchivedParticipantRepository archivedParticipantRepository;
    final ArchivedPerformanceRepository archivedPerformanceRepository;

    final ParticipantRepository participantRepository;
    final InternshipRepository internshipRepository;
    final PerformanceRepository performanceRepository;
    final UserRepository userRepository;
    final GitlabService gitlabService;
    final UserService userService;
    final ForkService forkService;
    final TaskService taskService;

    public void addArchivedInternship(ArchivedInternshipEntity archivedInternshipEntity) {
        archivedInternshipRepository.save(archivedInternshipEntity);
    }

    public void addArchivedParticipant(ArchivedParticipantEntity archivedParticipantEntity) {
        archivedParticipantRepository.save(archivedParticipantEntity);
    }

    public void addArchivedPerformance(ArchivedPerformanceEntity archivedPerformanceEntity) {
        archivedPerformanceRepository.save(archivedPerformanceEntity);
    }

    public void archiveParticipant(Long internshipId, Long participantId) {
        InternshipEntity internshipEntity = internshipRepository.getById(internshipId);
        if (internshipEntity == null)
            throw new NullPointerException("Internship null");

        // архивируем стажировку
        ArchivedInternshipEntity archivedInternshipEntity = InternshipArchivedInternshipMapper.MAPPER.toArchivedInternship(internshipEntity);
        archivedInternshipRepository.save(archivedInternshipEntity);

        ParticipantEntity participant = participantRepository.getById(participantId);

        if (participant == null)
            throw new NullPointerException("Participant null");

        // архивируем участника
        ArchivedParticipantEntity archivedParticipantEntity = ParticipantArchivedParticipantMapper.MAPPER.toArchivedParticipant(participant);
        archivedParticipantEntity.setInternship(archivedInternshipEntity);
        addArchivedParticipant(archivedParticipantEntity);

        // получаем список успеваемости участника
        List<PerformanceEntity> performanceEntityList = performanceRepository.getAllByParticipant(participant);

        // архивируем успеваемость участника
        for (PerformanceEntity performanceEntity : performanceEntityList) {
            ArchivedPerformanceEntity archivedPerformanceEntity = PerformanceArchivedPerformanceMapper.MAPPER.toArchivedPerformance(performanceEntity);
            archivedPerformanceEntity.setParticipant(archivedParticipantEntity);
            addArchivedPerformance(archivedPerformanceEntity);

            // удаляем из базы устаревшую успеваемость участника
            performanceRepository.delete(performanceEntity);

            // удаляем из базы бывшего участника стажировки
            participantRepository.delete(participant);

            // удаляем пользователя из базы
            userRepository.removeUserByName(participant.getUsername());

            // удаляем пользователя из гитлаба
            gitlabService.deleteUserById(participant.getGitlabId());
        }
    }

    public void archiveInternship(Long internshipId) {
        InternshipEntity internshipEntity = internshipRepository.getById(internshipId);
        if (internshipEntity == null)
            throw new NullPointerException("Internship null");

        // архивируем стажировку
        ArchivedInternshipEntity archivedInternshipEntity = InternshipArchivedInternshipMapper.MAPPER.toArchivedInternship(internshipEntity);
        addArchivedInternship(archivedInternshipEntity);

        List<ParticipantEntity> participantEntityList = participantRepository.getAllByInternship(internshipEntity);
        if (participantEntityList == null)
            throw new NullPointerException("Participants null");

        for (ParticipantEntity participantEntity : participantEntityList) {
            // архивируем участников стажировки
            ArchivedParticipantEntity archivedParticipantEntity = ParticipantArchivedParticipantMapper.MAPPER.toArchivedParticipant(participantEntity);
            archivedParticipantEntity.setInternship(archivedInternshipEntity);
            addArchivedParticipant(archivedParticipantEntity);

            List<PerformanceEntity> performanceEntityList = performanceRepository.getAllByParticipant(participantEntity);
            if (performanceEntityList != null) {
                for (PerformanceEntity performanceEntity : performanceEntityList) {
                    // архивируем успеваемость участников
                    ArchivedPerformanceEntity archivedPerformanceEntity = PerformanceArchivedPerformanceMapper.MAPPER.toArchivedPerformance(performanceEntity);
                    archivedPerformanceEntity.setParticipant(archivedParticipantEntity);
                    addArchivedPerformance(archivedPerformanceEntity);

                    // удаляем из базы устаревшую успеваемость участников
                    performanceRepository.delete(performanceEntity);
                }
            }

            // удаляем из базы бывших участников стажировки
            participantRepository.delete(participantEntity);

            // удаляем пользователей из базы
            userService.removeUserByName(participantEntity.getUsername());

            // удаляем пользователей из гитлаба
            gitlabService.deleteUserById(participantEntity.getGitlabId());
        }

        // удаление из базы закончившейся стажировки
        internshipRepository.delete(internshipEntity);

        // удаление форков
        List<ForkEntity> forks = forkService.getAllForksByInternship(internshipId);
        for (ForkEntity fork : forks) {
            gitlabService.deleteProjectById(fork.getGitLabRepositoryId());
        }

        // удаление эталонных репозиториев
        List<TaskEntity> taskEntities = taskService.getAllByInternship(internshipEntity.getId());
        for (TaskEntity taskEntity : taskEntities) {
            gitlabService.deleteProjectById(taskEntity.getGitLabRepositoryId());
        }
    }
}
