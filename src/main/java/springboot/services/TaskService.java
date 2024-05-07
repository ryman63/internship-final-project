package springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.models.Internship;
import springboot.models.Task;
import springboot.repositories.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository repository;

    public void add(Task task)
    {
        repository.save(task);
    }

    public List<Task> getAllByInternship(Internship internship){
        return getAllByInternship(internship);
    }

    public Task getTaskById(Long id) {
        return repository.getById(id);
    }
}
