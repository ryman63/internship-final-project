import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import springboot.entities.UserEntity;
import springboot.repositories.UserRepository;
import springboot.services.EmailService;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendSimpleMessage() {
        // Arrange
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Message";

        // Act
        emailService.sendSimpleMessage(to, subject, text);

        // Assert
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendMessageAllAdmins() {
        // Arrange
        String subject = "Test Subject";
        String text = "Test Message";

        UserEntity admin1 = new UserEntity();
        admin1.setEmail("admin1@example.com");

        UserEntity admin2 = new UserEntity();
        admin2.setEmail("admin2@example.com");

        List<UserEntity> admins = new ArrayList<>();
        admins.add(admin1);
        admins.add(admin2);

        when(userRepository.getAllAdmins()).thenReturn(admins);

        // Act
        emailService.sendMessageAllAdmins(subject, text);

        // Assert
        verify(emailSender, times(2)).send(any(SimpleMailMessage.class));
    }
}
