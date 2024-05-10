import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springboot.dto.UserDto;
import springboot.entities.AuthorityEntity;
import springboot.entities.ParticipantEntity;
import springboot.entities.UserEntity;
import springboot.mapper.ParticipantEntityUserDtoMapper;
import springboot.mapper.UserDtoUserEntityMapper;
import springboot.repositories.AuthorityRepository;
import springboot.repositories.ParticipantRepository;
import springboot.repositories.UserRepository;
import springboot.services.UserService;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private UserDtoUserEntityMapper userMapper;

    @Mock
    private ParticipantEntityUserDtoMapper participantMapper;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testRemoveUserByName() {
        String username = "testuser";

        userService.removeUserByName(username);

        verify(userRepository, times(1)).removeUserByName(username);
        verify(authorityRepository, times(1)).removeAuthorityByName(username);
    }

    @Test
    public void testGetUserByParticipantId() {
        Long participantId = 1L;
        String username = "testuser";
        ParticipantEntity participantEntity = new ParticipantEntity();
        participantEntity.setUsername(username);

        when(participantRepository.getById(participantId)).thenReturn(participantEntity);
        when(userRepository.getUsersByName(username)).thenReturn(new UserEntity());

        UserEntity userEntity = userService.getUserByParticipantId(participantId);

        verify(participantRepository, times(1)).getById(participantId);
        verify(userRepository, times(1)).getUsersByName(username);
        assert(userEntity != null);
    }

}
