package springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.models.ArchivedInternship;
import springboot.models.ArchivedParticipant;
import springboot.models.ArchivedPerformance;
import springboot.models.Internship;
import springboot.repositories.ArchivedInternshipRepository;
import springboot.repositories.ArchivedParticipantRepository;
import springboot.repositories.ArchivedPerformanceRepository;

@Service
public class ArchivedService {
    @Autowired
    final ArchivedInternshipRepository archivedInternshipRepository;
    @Autowired
    final ArchivedParticipantRepository archivedParticipantRepository;
    @Autowired
    final ArchivedPerformanceRepository archivedPerformanceRepository;
    public ArchivedService(ArchivedInternshipRepository archivedInternshipRepository, ArchivedParticipantRepository archivedParticipantRepository, ArchivedPerformanceRepository archivedPerformanceRepository) {
        this.archivedInternshipRepository = archivedInternshipRepository;
        this.archivedParticipantRepository = archivedParticipantRepository;
        this.archivedPerformanceRepository = archivedPerformanceRepository;
    }

    public void addArchivedInternship(ArchivedInternship archivedInternship) {
        archivedInternshipRepository.save(archivedInternship);
    }
    public void addArchivedParticipant(ArchivedParticipant archivedParticipant) {
        archivedParticipantRepository.save(archivedParticipant);
    }
    public void addArchivedPerformance(ArchivedPerformance archivedPerformance) {
        archivedPerformanceRepository.save(archivedPerformance);
    }
}
