package springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.dto.LessonDto;
import springboot.entities.*;
import springboot.mapper.LessonDtoLessonEntityMapper;
import springboot.repositories.InternshipRepository;
import springboot.repositories.LessonRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {

    final LessonRepository lessonRepository;
    final InternshipRepository internshipRepository;
    final ParticipantService participantService;
    final GitlabService gitlabService;
    final ForkService forkService;

    public LessonEntity save(LessonDto lessonDto) {
        return lessonRepository.save(LessonDtoLessonEntityMapper.MAPPER.toLessonEntity(lessonDto));
    }

    public LessonEntity update(LessonDto lessonDto, Long lessonId) {
        LessonEntity lessonEntity = lessonRepository.getById(lessonId);
        lessonEntity.setStatus(lessonDto.getStatus());
        lessonEntity.setName(lessonDto.getName());
        lessonEntity.setPublicationDateTime(lessonDto.getPublicationDateTime());
        return lessonRepository.save(lessonEntity);
    }

    public void delete(Long lessonId) {
        lessonRepository.delete(lessonRepository.getById(lessonId));
    }

    // публикует занятие по id и возвращает кол-во созданных форков
    public int lessonPublication(Long lessonId) throws Exception {
        int counterForks = 0;

        LessonEntity lessonEntity = lessonRepository.getById(lessonId);
        if (lessonEntity == null)
            throw new Exception("Lesson not found");
        List<TaskEntity> taskEntityList = lessonRepository.getTasks(lessonEntity);
        if (taskEntityList == null)
            throw new Exception("Tasks not found");

        // получение всех активных участников
        List<ParticipantEntity> activeParticipantEntities = participantService.getParticipantsByInternship(lessonEntity.getInternship());

        for (TaskEntity taskEntity : taskEntityList) {
            for (ParticipantEntity participantEntity : activeParticipantEntities) {
                counterForks++;

                Long createForkId = gitlabService.createFork(taskEntity, participantEntity.getUsername());

                // создаём объект fork и заполняем
                ForkEntity forkEntity = ForkEntity.builder()
                        .task(taskEntity)
                        .lesson(lessonEntity)
                        .gitLabRepositoryId(String.valueOf(createForkId))
                        .participant(participantService.getParticipantByUsername(participantEntity.getUsername()))
                        .build();
                // записываем в базу
                forkService.add(forkEntity);
            }
        }
        // обновляем статус занятия
        lessonEntity.setStatus("опубликовано");
        lessonRepository.save(lessonEntity);

        return counterForks;
    }
    public LessonEntity getLessonById(Long id) {
        return lessonRepository.getById(id);
    }

    public LessonEntity createWithInternship(LessonDto lessonDto, Long internshipId) {
        LessonEntity entity = LessonDtoLessonEntityMapper.MAPPER.toLessonEntity(lessonDto);
        entity.setInternship(internshipRepository.getById(internshipId));
        return lessonRepository.save(entity);
    }
}
