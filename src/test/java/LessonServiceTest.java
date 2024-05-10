import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.dto.LessonDto;
import springboot.entities.InternshipEntity;
import springboot.entities.LessonEntity;
import springboot.entities.ParticipantEntity;
import springboot.entities.TaskEntity;
import springboot.mapper.LessonDtoLessonEntityMapper;
import springboot.repositories.InternshipRepository;
import springboot.repositories.LessonRepository;
import springboot.services.GitlabService;
import springboot.services.LessonService;
import springboot.services.ParticipantService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;


    @Mock
    private LessonDtoLessonEntityMapper lessonMapper;

    @InjectMocks
    private LessonService lessonService;

    public LessonServiceTest() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testDelete() {
        Long lessonId = 1L;
        LessonEntity lessonEntity = new LessonEntity();
        when(lessonRepository.getById(lessonId)).thenReturn(lessonEntity);

        lessonService.delete(lessonId);

        verify(lessonRepository, times(1)).delete(lessonEntity);
    }


    @Test
    public void testGetLessonById() {
        Long lessonId = 1L;
        LessonEntity lessonEntity = new LessonEntity();
        when(lessonRepository.getById(lessonId)).thenReturn(lessonEntity);

        LessonEntity result = lessonService.getLessonById(lessonId);

        assert(result != null);
        verify(lessonRepository, times(1)).getById(lessonId);
    }

    @Test
    void getLessonById_shouldReturnLessonEntity() {
        Long lessonId = 1L;
        LessonEntity expectedLesson = new LessonEntity();
        when(lessonRepository.getById(lessonId)).thenReturn(expectedLesson);

        LessonEntity lessonById = lessonService.getLessonById(lessonId);

        assertEquals(expectedLesson, lessonById);
    }

}
