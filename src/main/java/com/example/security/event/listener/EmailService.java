// package com.example.security.event.listener;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.event.EventListener;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import org.springframework.stereotype.Component;


// import jakarta.mail.MessagingException;
// import jakarta.mail.internet.MimeMessage;

// @Component
// public class EmailService {

//     @Autowired
//     private JavaMailSender mailSender;

//     @EventListener
//     public void handleUserRegisteredEvent(FeedbackEvent event) throws MessagingException {
//         MimeMessage mimeMessage = mailSender.createMimeMessage();
//         MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//         mimeMessageHelper.setTo(event.getFeedback().getEmail());
//         mimeMessageHelper.setSubject("Review feedback");
//         mimeMessageHelper.setText("Thank you "+ event.getFeedback().getName()+" for your comments");
//         mailSender.send(mimeMessage);
//         System.out.println("Email sent to " + event.getFeedback().getEmail());
//     }
// }
