package com.caldev.wishlister.services;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    public void sendEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mailtest111999@gmail.com",
                        "egsa iajz uycr evah");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom("mailtest111999@gmail.com");
            message.addRecipient(javax.mail.Message.RecipientType.TO, new javax.mail.internet.InternetAddress("mailtest221999@gmail.com"));
            message.setSubject("test");
            message.setText("test");
            javax.mail.Transport.send(message);
            System.out.println("Sending email to " + "mailtest221999@gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }


//    private final JavaMailSender mailSender;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    @Async
//    public void sendEmail(String to, String subject, String body) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        mailSender.send(message);
//
//        mailSender.send(message);
//    }
    }
}
