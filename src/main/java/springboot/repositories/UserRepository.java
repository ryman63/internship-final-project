package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.entities.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.username = ?1")
    public UserEntity getUsersByName(String username);

    @Modifying
    @Query("DELETE UserEntity u where u.username = ?1")
    public UserEntity removeUserByName(String username);
    @Query("select u from UserEntity u, AuthorityEntity auth where auth.user = u and auth.authority = 'ROLE_ADMIN'")
    public List<UserEntity> getAllAdmins();
}
