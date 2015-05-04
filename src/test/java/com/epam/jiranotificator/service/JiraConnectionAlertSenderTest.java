/**
 * 
 */
package com.epam.jiranotificator.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.jiranotificator.configuration.context.event.JiraConnectionAlertEvent;
import com.epam.jiranotificator.service.impl.EmailSender;
import com.epam.jiranotificator.service.impl.JiraConnectionAlertSender;

/**
 * @author Bohdan_Kolesnyk
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JiraConnectionAlertSenderTest {

    @Mock
    private EmailSender emailSender;
    @Spy
    @InjectMocks
    private JiraConnectionAlertSender jiraConnectionAlertSender = new JiraConnectionAlertSender();

    @Test
    public void shouldSuccessfulHandledEvent() {
        final JiraConnectionAlertEvent jiraConnectionAlertEvent = mock(JiraConnectionAlertEvent.class);
        final String subject = "Test subject";
        final String text = "Test text";

        when(jiraConnectionAlertEvent.getSubject()).thenReturn(subject);
        when(jiraConnectionAlertEvent.getText()).thenReturn(text);

        jiraConnectionAlertSender.onApplicationEvent(jiraConnectionAlertEvent);
        verify(emailSender).send(subject, text);
    }

    @Test
    public void shouldFailedDueToEventNotPublished() {
        final JiraConnectionAlertEvent jiraConnectionAlertEvent = mock(JiraConnectionAlertEvent.class);
        final String subject = "Test subject";
        final String text = "Test text";

        when(jiraConnectionAlertEvent.getSubject()).thenReturn(subject);
        when(jiraConnectionAlertEvent.getText()).thenReturn(text);

        verify(emailSender, never()).send(subject, text);
    }
}
