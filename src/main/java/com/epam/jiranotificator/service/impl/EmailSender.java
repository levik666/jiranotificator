package com.epam.jiranotificator.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

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

    private MailSender mailSender;
    private String emailFrom;
    private String [] emailsTo;
    
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void setEmailFrom(String emailFrom){
		this.emailFrom = emailFrom;
	}
	
	public void setEmailsTo(String [] emailsTo){
		this.emailsTo = emailsTo;
	}
 
	/**
	 * Send email
	 * @param subject
	 * @param msg
	 */
	public void send(String subject, String msg) {
 
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setFrom(emailFrom);
		message.setTo(emailsTo);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
		LOG.debug("Email to " + Arrays.toString(emailsTo) + " sent.");
	}
}
