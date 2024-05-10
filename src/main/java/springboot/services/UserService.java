package springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.dto.UserDto;
import springboot.entities.AuthorityEntity;
import springboot.entities.ParticipantEntity;
import springboot.entities.UserEntity;
import springboot.mapper.ParticipantEntityUserDtoMapper;
import springboot.mapper.UserDtoUserEntityMapper;
import springboot.repositories.AuthorityRepository;
import springboot.repositories.ParticipantRepository;
import springboot.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final AuthorityRepository authorityRepository;
    final ParticipantRepository participantRepository;

    public UserEntity getUserByName(String username) {
        return userRepository.getUsersByName(username);
    }

    public UserEntity saveUser(UserDto userDto) throws Exception {
        if (userDto == null)
            throw new Exception("user is null");
        userDto.setPassword("{noop}" + userDto.getPassword());

        UserEntity entity = UserDtoUserEntityMapper.MAPPER.toUserEntity(userDto);
        entity.setEnabled(true);

        AuthorityEntity userAuthorityEntity = new AuthorityEntity();
        userAuthorityEntity.setUser(entity);
        userAuthorityEntity.setAuthority("ROLE_USER");

        UserEntity outEntity = userRepository.save(entity);
        authorityRepository.save(userAuthorityEntity);
        return outEntity;
    }

    public void saveAdmin(UserDto userDto) throws Exception {
        if (userDto == null)
            throw new Exception("user is null");

        userDto.setPassword("{noop}" + userDto.getPassword());

        UserEntity entity = UserDtoUserEntityMapper.MAPPER.toUserEntity(userDto);
        entity.setEnabled(true);

        AuthorityEntity userAuthorityEntity = new AuthorityEntity();
        userAuthorityEntity.setUser(entity);
        userAuthorityEntity.setAuthority("ROLE_ADMIN");

        userRepository.save(entity);
        authorityRepository.save(userAuthorityEntity);
    }

    public void removeUserByName(String username) {
        userRepository.removeUserByName(username);
        authorityRepository.removeAuthorityByName(username);
    }

    public UserEntity getUserByParticipantId(Long participantId) {

        ParticipantEntity participantEntity = participantRepository.getById(participantId);
        if (participantEntity == null)
            return null;
        String username = participantEntity.getUsername();
        return userRepository.getUsersByName(username);

    }

    public UserEntity createUserByParticipant(Long participantId) throws Exception {
        ParticipantEntity participantEntity = participantRepository.getById(participantId);

        UserDto userDto = ParticipantEntityUserDtoMapper.MAPPER.toUserDto(participantEntity);

        // выставляем пароль равный username
        userDto.setPassword(userDto.getUsername());
        // создание нового пользователя с ролью User
        return saveUser(userDto);
    }
}
