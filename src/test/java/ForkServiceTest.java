import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.entities.ForkEntity;
import springboot.entities.InternshipEntity;
import springboot.entities.LessonEntity;
import springboot.repositories.ForkRepository;
import springboot.repositories.InternshipRepository;
import springboot.repositories.LessonRepository;
import springboot.services.ForkService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ForkServiceTest {

    @Mock
    private ForkRepository forkRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private InternshipRepository internshipRepository;

    @InjectMocks
    private ForkService forkService;

    public ForkServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd() {
        ForkEntity forkEntity = new ForkEntity();

        forkService.add(forkEntity);

        verify(forkRepository, times(1)).save(forkEntity);
    }

    @Test
    public void testGetAllForksByLesson() {
        Long lessonId = 1L;
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setId(lessonId);
        List<ForkEntity> expectedForks = new ArrayList<>();
        when(lessonRepository.getById(lessonId)).thenReturn(lessonEntity);
        when(forkRepository.findAllByLesson(lessonEntity)).thenReturn(expectedForks);

        List<ForkEntity> result = forkService.getAllForksByLesson(lessonId);

        assertEquals(expectedForks, result);
    }

    @Test
    public void testGetAllForksByInternship() {
        Long internshipId = 1L;
        InternshipEntity internshipEntity = new InternshipEntity();
        internshipEntity.setId(internshipId);
        List<ForkEntity> expectedForks = new ArrayList<>();
        when(internshipRepository.getById(internshipId)).thenReturn(internshipEntity);
        when(forkRepository.findAllByInternship(internshipEntity)).thenReturn(expectedForks);

        List<ForkEntity> result = forkService.getAllForksByInternship(internshipId);

        assertEquals(expectedForks, result);
    }

}
