package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.configuration.context.event.AlertEvent;
import com.epam.jiranotificator.service.impl.AlertPushNotificationSender;
import com.epam.jiranotificator.service.impl.GCMNotificationSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AlertPushNotificationSenderTest {

    @Mock
    private GCMNotificationSender gcmNotificationSender;
    @Spy
    @InjectMocks
    private AlertPushNotificationSender alertPushNotificationSender = new AlertPushNotificationSender();

    @Test
    public void shouldSuccessfulHandledEvent() {
        final AlertEvent alertEvent = mock(AlertEvent.class);
        final Issue issue = mock(Issue.class);

        when(alertEvent.getIssue()).thenReturn(issue);

        alertPushNotificationSender.onApplicationEvent(alertEvent);
        verify(gcmNotificationSender).send(issue);
    }

    @Test
    public void shouldFailedDueToEventNotPublished() {
        final AlertEvent alertEvent = mock(AlertEvent.class);
        final Issue issue = mock(Issue.class);

        when(alertEvent.getIssue()).thenReturn(issue);

        verify(gcmNotificationSender,never()).send(issue);
    }
}
