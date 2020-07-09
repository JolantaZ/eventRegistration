package com.gmail.jolantazych.event_register.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String content) throws MessagingException {
        MimeMessage mail = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(to);
        helper.setFrom("javajavadoodoo@gmail.com");
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(mail);

    }


}
