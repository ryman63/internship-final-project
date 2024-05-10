package springboot.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import springboot.entities.UserEntity;
import springboot.repositories.UserRepository;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender emailSender;
    private UserRepository userRepository;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendMessageAllAdmins(String subject, String text) {
        for (UserEntity userEntity : userRepository.getAllAdmins()) {
            sendSimpleMessage(userEntity.getEmail(), subject, text);
        }
    }
}
