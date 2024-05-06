package springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.models.Participant;
import springboot.models.Performance;
import springboot.repositories.PerformanceRepository;

import java.util.List;

@Service
public class PerformanceService {
    @Autowired
    final PerformanceRepository performanceRepository;

    public PerformanceService(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    public List<Performance> getPerformancesByParticipant(Participant participant) {
        return performanceRepository.getAllByParticipant(participant);
    }

    public void removePerformance(Performance performance){
        performanceRepository.delete(performance);
    }
}
