package com.epam.jiranotificator.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.epam.jiranotificator.exception.EmailSenderException;

/**
 * Service which send emails to specific address
 * 
 * @author Bohdan_Kolesnyk
 *
 */
@Service("emailSender")
public class EmailSender {

    private static final Logger LOG = LoggerFactory
            .getLogger(EmailSender.class);

    private String emailFrom;
    private String emailTo;
    private String emailLogin;
    private String emailPassword;

    public EmailSender(String emailFrom, String emailTo, String emailLogin,
            String emailPassword) {
        super();
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.emailLogin = emailLogin;
        this.emailPassword = emailPassword;
    }

    public void send(String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailLogin,
                                emailPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            LOG.debug("Email to " + emailTo + " send.");

        } catch (MessagingException e) {
            throw new EmailSenderException(e.getMessage());
        }
    }
}
