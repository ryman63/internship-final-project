package springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.models.Internship;
import springboot.models.Participant;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM Participant u " +
            "WHERE u.username = ?1 or u.email = ?2 or u.telegramId = ?3 or u.mobileNumber = ?4")
    public boolean existByUsernameEmailTelegramIdMobileNumber(String username, String email, String telegramId, String mobileNumber);

    @Query("select participant from Participant participant where participant.internship = ?1")
    public List<Participant> getAllByInternship(Internship internship);
}
