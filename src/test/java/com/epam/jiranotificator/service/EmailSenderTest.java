/**
 * 
 */
package com.epam.jiranotificator.service;

import static org.mockito.Mockito.doThrow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.jiranotificator.exception.EmailSenderException;
import com.epam.jiranotificator.service.impl.EmailSender;

/**
 * @author Bohdan_Kolesnyk
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailSenderTest {
    
    private static final String FROM = "from@gmail.com";
    private static final String TO = "to@gmail.com";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String SUBJECT = "subject";
    private static final String TEXT = "text";
    
    @Spy
    @InjectMocks
    private EmailSender emailSender = new EmailSender(FROM, TO, LOGIN, PASSWORD);

//    @Test
//    public void shouldSuccessfulSendMessage() {
//        emailSender.send(SUBJECT, TEXT);
//    }

    @Test(expected = EmailSenderException.class)
    public void shouldThrowException() throws Exception {
        doThrow(new EmailSenderException("Can't send")).when(emailSender).send(SUBJECT, TEXT);
        emailSender.send(SUBJECT, TEXT);
    }
}
