/**
 * 
 */
package com.epam.jiranotificator.service;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.epam.jiranotificator.exception.EmailSenderException;
import com.epam.jiranotificator.service.impl.EmailSender;

/**
 * @author Bohdan_Kolesnyk
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailSenderTest {
    
    private static final String SUBJECT = "subject";
    private static final String TEXT = "text";
    private static final String FROM = "test";
    private static final String [] TO = new String[]{"test", "test"};
    
    private SimpleMailMessage message;
    
    @Mock
    private MailSender mailSender;
    
    @Spy
    @InjectMocks
    private EmailSender emailSender = new EmailSender();

    @Before
    public void setup(){
    	emailSender.setMailSender(mailSender);
    	emailSender.setEmailFrom(FROM);
    	emailSender.setEmailsTo(TO);
    	message = new SimpleMailMessage();
    	message.setFrom(FROM);
    	message.setTo(TO);
    	message.setSubject(SUBJECT);
    	message.setText(TEXT);
    }
    
    @Test
    public void shouldSuccessfulSendMessage() {
        emailSender.send(SUBJECT, TEXT);
        verify(mailSender).send(message);
    }

    @Test(expected = EmailSenderException.class)
    public void shouldThrowException() throws Exception {
        doThrow(new EmailSenderException("Can't send")).when(emailSender).send(SUBJECT, TEXT);
        emailSender.send(SUBJECT, TEXT);
    }
}
