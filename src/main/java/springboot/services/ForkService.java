package springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.models.Fork;
import springboot.models.Internship;
import springboot.models.Lesson;
import springboot.repositories.ForkRepository;

import java.util.List;

@Service
public class ForkService {

    @Autowired
    final ForkRepository forkRepository;


    public ForkService(ForkRepository forkRepository) {
        this.forkRepository = forkRepository;
    }

    public void add(Fork fork){
        forkRepository.save(fork);
    }

    public List<Fork> getAllForksByLesson(Lesson lesson) {
        return forkRepository.findAllByLesson(lesson);
    }

    public List<Fork> getAllForksByInternship(Internship internship) { return  forkRepository.findAllByInternship(internship);}

    public void removeAllByInternship(Internship internship) {
        forkRepository.removeAllByInternship(internship);
    }
}
