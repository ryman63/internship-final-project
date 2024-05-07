package springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.models.Authority;
import springboot.models.User;
import springboot.repositories.AuthorityRepository;
import springboot.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    final UserRepository userRepository;
    @Autowired
    final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public User getUserByName(String username){
        return userRepository.getUsersByName(username);
    }

    public void addUser(User user, Authority userAuthority) {
        userRepository.save(user);
        authorityRepository.save(userAuthority);
    }

    public void removeUserByName(String username) {
        userRepository.removeUserByName(username);
        authorityRepository.removeAuthorityByName(username);
    }
}
