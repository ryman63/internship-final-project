package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    @Modifying
    @Query("delete Authority auth where auth.user.username = ?1")
    public void removeAuthorityByName(String username);
}
