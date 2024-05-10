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
        // Arrange
        Long internshipId = 1L;
        InternshipEntity entity = new InternshipEntity();
        when(repository.getById(internshipId)).thenReturn(entity);

        // Act
        InternshipEntity result = service.getInternshipById(internshipId);

        // Assert
        assert(result != null);
        verify(repository, times(1)).getById(internshipId);
    }

    @Test
    public void testRemoveInternship() {
        // Arrange
        InternshipEntity entity = new InternshipEntity();

        // Act
        service.removeInternship(entity);

        // Assert
        verify(repository, times(1)).delete(entity);
    }
}
