package springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.models.Lesson;
import springboot.repositories.LessonRepository;

@Service
public class LessonService {
    @Autowired
    final LessonRepository repository;
    public LessonService(LessonRepository repository) {
        this.repository = repository;
    }
    public void add(Lesson lesson){
        repository.save(lesson);
    }

    public Lesson getLessonById(Long id){
        return repository.getById(id);
    }
}
