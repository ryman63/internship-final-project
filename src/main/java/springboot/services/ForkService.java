package springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.entities.ForkEntity;
import springboot.entities.InternshipEntity;
import springboot.entities.LessonEntity;
import springboot.repositories.ForkRepository;
import springboot.repositories.InternshipRepository;
import springboot.repositories.LessonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ForkService {

    final ForkRepository forkRepository;
    final LessonRepository lessonRepository;
    final InternshipRepository internshipRepository;

    public void add(ForkEntity fork) {
        forkRepository.save(fork);
    }
    public List<ForkEntity> getAllForksByLesson(Long lessonId) {
        LessonEntity lessonEntity = lessonRepository.getById(lessonId);
        return forkRepository.findAllByLesson(lessonEntity);
    }

    public List<ForkEntity> getAllForksByInternship(Long internshipId) {
        InternshipEntity internshipEntity = internshipRepository.getById(internshipId);
        return forkRepository.findAllByInternship(internshipEntity);
    }

    public void removeAllByInternship(Long internshipId) {
        InternshipEntity internshipEntity = internshipRepository.getById(internshipId);
        forkRepository.removeAllByInternship(internshipEntity);
    }
}
