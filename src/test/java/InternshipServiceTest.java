import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.dto.InternshipDto;
import springboot.entities.InternshipEntity;
import springboot.mapper.InternshipDtoInternshipEntityMapper;
import springboot.repositories.InternshipRepository;
import springboot.services.InternshipService;

import static org.mockito.Mockito.*;

public class InternshipServiceTest {

    @Mock
    private InternshipRepository repository;

    @Mock
    private InternshipDtoInternshipEntityMapper internshipMapper;

    @InjectMocks
    private InternshipService service;

    public InternshipServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetInternshipById() {
        Long internshipId = 1L;
        InternshipEntity entity = new InternshipEntity();
        when(repository.getById(internshipId)).thenReturn(entity);

        InternshipEntity result = service.getInternshipById(internshipId);

        assert(result != null);
        verify(repository, times(1)).getById(internshipId);
    }

    @Test
    public void testRemoveInternship() {
        InternshipEntity entity = new InternshipEntity();

        service.removeInternship(entity);

        verify(repository, times(1)).delete(entity);
    }
}
