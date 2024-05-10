import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.dto.ParticipantDto;
import springboot.dto.ParticipantUpdateDto;
import springboot.entities.InternshipEntity;
import springboot.entities.ParticipantEntity;
import springboot.mapper.ParticipantDtoParticipantEntityMapper;
import springboot.repositories.ParticipantRepository;
import springboot.services.InternshipService;
import springboot.services.ParticipantService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private ParticipantDtoParticipantEntityMapper participantMapper;

    @InjectMocks
    private ParticipantService participantService;

    public ParticipantServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetParticipantById() {
        Long participantId = 1L;
        ParticipantEntity participantEntity = new ParticipantEntity();
        when(participantRepository.getById(participantId)).thenReturn(participantEntity);

        ParticipantEntity retrievedParticipantEntity = participantService.getParticipantById(participantId);

        assert(retrievedParticipantEntity != null);
        assert(retrievedParticipantEntity.equals(participantEntity));
        verify(participantRepository, times(1)).getById(participantId);
    }

//    @Test
//    public void testSignUpParticipantForInternship() throws Exception {
//        // Arrange
//        Long participantId = 1L;
//        Long internshipId = 1L;
//        Long gitlabUserId = 123L;
//        InternshipEntity internshipEntity = new InternshipEntity();
//        ParticipantEntity participantEntity = new ParticipantEntity();
//        when(participantRepository.getById(participantId)).thenReturn(participantEntity);
//        when(internshipService.getInternshipById(internshipId)).thenReturn(internshipEntity);
//
//        // Act
//        participantService.signUpParticipantForInternship(participantId, internshipId, gitlabUserId);
//
//        // Assert
//        assert(participantEntity.getInternshipEntity() != null);
//        // Add assertions as per your requirement
//    }

    @Test
    public void testCheckExistsParticipant() {
        ParticipantEntity participantEntity = new ParticipantEntity();
        when(participantRepository.existByUsernameEmailTelegramIdMobileNumber(any(), any(), any(), any())).thenReturn(true);

        boolean existsParticipant = participantService.checkExistsParticipant(participantEntity);

        assert(existsParticipant);
        verify(participantRepository, times(1)).existByUsernameEmailTelegramIdMobileNumber(any(), any(), any(), any());
    }

    @Test
    public void testGetParticipantsByInternship() {
        InternshipEntity internshipEntity = new InternshipEntity();
        List<ParticipantEntity> participantEntities = new ArrayList<>();
        when(participantRepository.getAllByInternship(internshipEntity)).thenReturn(participantEntities);

        List<ParticipantEntity> retrievedParticipantEntities = participantService.getParticipantsByInternship(internshipEntity);

        assert(retrievedParticipantEntities != null);
        assert(retrievedParticipantEntities.equals(participantEntities));
        verify(participantRepository, times(1)).getAllByInternship(internshipEntity);
    }

    @Test
    public void testRemoveParticipant() {
        ParticipantEntity participantEntity = new ParticipantEntity();

        participantService.removeParticipant(participantEntity);

        verify(participantRepository, times(1)).delete(participantEntity);
    }

    @Test
    public void testGetParticipantByUsername() {
        String username = "testuser";
        ParticipantEntity participantEntity = new ParticipantEntity();
        when(participantRepository.getParticipantByUsername(username)).thenReturn(participantEntity);

        ParticipantEntity retrievedParticipantEntity = participantService.getParticipantByUsername(username);

        assert(retrievedParticipantEntity != null);
        assert(retrievedParticipantEntity.equals(participantEntity));
        verify(participantRepository, times(1)).getParticipantByUsername(username);
    }

//    @Test
//    public void testUpdate() {
//        // Arrange
//        Long participantId = 1L;
//        ParticipantUpdateDto participantDto = new ParticipantUpdateDto();
//        ParticipantEntity participantEntity = new ParticipantEntity();
//        when(participantRepository.getById(participantId)).thenReturn(participantEntity);
//
//        // Act
//        ParticipantEntity updatedParticipantEntity = participantService.update(participantDto, participantId);
//
//        // Assert
//        assert(updatedParticipantEntity != null);
//        verify(participantRepository, times(1)).save(participantEntity);
//    }

    @Test
    public void testDelete() {
        Long participantId = 1L;
        ParticipantEntity participantEntity = new ParticipantEntity();
        when(participantRepository.getById(participantId)).thenReturn(participantEntity);

        participantService.delete(participantId);

        verify(participantRepository, times(1)).delete(participantEntity);
    }
}
