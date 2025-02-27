package com.example.security.services.passwordresettoken;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.security.entities.PasswordResetToken;
import com.example.security.entities.User;
import com.example.security.repositories.PasswordResetTokenRepository;
import com.example.security.repositories.UserRepository;
import com.example.security.services.user.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender JavaMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void sendResetPasswordEmail(String email) {
        User user = userService.findByEmail(email);

        String token = UUID.randomUUID().toString();
        PasswordResetToken existingToken = passwordResetTokenRepository.findByUser(user);
        if (existingToken != null) {
            existingToken.setToken(token);
            existingToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            passwordResetTokenRepository.save(existingToken);
        } else {
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            passwordResetTokenRepository.save(resetToken);
        }

        String resetUrl = "http://localhost:8080/reset-password?token=" + token;
        String message = "Click the link to reset your password: " + resetUrl;

        try {
            sendEmail(user.getEmail(), "Password Reset Request", message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String to, String subject, String text) throws MessagingException {

        MimeMessage mimeMessage = JavaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        JavaMailSender.send(mimeMessage);
    }

    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElse(null); // Trả về null nếu không tìm thấy

        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false; // Token không tồn tại hoặc đã hết hạn
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);

        return true;
    }
}
