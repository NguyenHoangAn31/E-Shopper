package com.example.security.services.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.security.dto.ContactDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ContactService {
    @Autowired
    private JavaMailSender JavaMailSender;

    public void sendEmailContact(ContactDto contactDto) throws MessagingException {
        MimeMessage mimeMessage = JavaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo("anphung311002@gmail.com");
        mimeMessageHelper.setSubject("Contact from " + contactDto.getEmail());
        mimeMessageHelper.setText(contactDto.getMessage());
        JavaMailSender.send(mimeMessage);
    }
}
