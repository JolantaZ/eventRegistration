package com.gmail.jolantazych.event_register.service;

import com.gmail.jolantazych.event_register.model.Event;
import com.gmail.jolantazych.event_register.model.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
public class MailService {

    private final static String FROM_MAIL = "javajavadoodoo@gmail.com";

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    private static final String MAIL_SUBJECT = "Confirmation of registration for the event";
    final static String BANK_ACCOUNT = "93 2490 0005 8077 2308 5460 5439";

    public MailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(User user) throws MessagingException {
        // MIME - Multipurpose Internet Mail Extensions to standard stosowany przy przesyłaniu poczty elektronicznej
        MimeMessage mail = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(user.getEmail());
        helper.setFrom(FROM_MAIL);
        helper.setSubject(MAIL_SUBJECT);

        String content = completeEmailContent(user);
        helper.setText(content, true);

        javaMailSender.send(mail);

    }

    private String completeEmailContent(User user) {

        Event event = user.getEvent();

        Context context = new Context();

        context.setVariable("header", "Event registration confirmation");
        context.setVariable("email", user.getEmail());
        context.setVariable("userName", user.getName());
        context.setVariable("eventTitle", event.getTitle());
        context.setVariable("eventTerm", event.getTerm().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // ??? będzie ok?
        context.setVariable("eventPrice", BigDecimal.valueOf(event.getPrice()).toString());
        context.setVariable("bankAccount", BANK_ACCOUNT);

        String mailView = templateEngine.process("mailView", context);
        return mailView;
    }


}
