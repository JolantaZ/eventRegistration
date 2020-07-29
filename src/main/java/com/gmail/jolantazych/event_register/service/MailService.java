package com.gmail.jolantazych.event_register.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    private final static String FROM_MAIL = "javajavadoodoo@gmail.com";

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String to, String subject, String content) throws MessagingException {
        // MIME - Multipurpose Internet Mail Extensions to standard stosowany przy przesyłaniu poczty elektronicznej
        MimeMessage mail = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(to);
        helper.setFrom(FROM_MAIL);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(mail);

    }


}
