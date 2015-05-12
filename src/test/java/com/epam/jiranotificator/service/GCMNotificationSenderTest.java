package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.exception.GCMException;
import com.epam.jiranotificator.service.impl.GCMNotificationSender;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class GCMNotificationSenderTest {

    private final String TICKET_NUMBER = "test-1234";

    private final String regIds = new String();
    private final int timeToLive = 30;
    private final int retries = 5;

    @Spy
    @InjectMocks
    private GCMNotificationSender gcmNotificationSender = new GCMNotificationSender("", "", "", "");

    @Test
    public void shouldSuccessfulSendMessage() {
        final Issue issue = mock(Issue.class);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);
        gcmNotificationSender.send(issue);
    }

    @Test(expected = GCMException.class)
    public void shouldThrowExceptionWhenPerformIssuesByQuery() throws Exception {
        final Issue issue = mock(Issue.class);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);
        doThrow(new GCMException("Can't send")).when(gcmNotificationSender).send(issue);
        gcmNotificationSender.send(issue);
    }

    @Test(expected = GCMException.class)
    public void shouldThrowExceptionWithStackTraceWhenPerformIssuesByQuery() throws Exception {
        final Issue issue = mock(Issue.class);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);
        doThrow(new GCMException("Can't send", new Throwable())).when(gcmNotificationSender).send(issue);
        gcmNotificationSender.send(issue);
    }

/*    @Test(expected = GCMException.class)
    public void shouldThrowExceptionWhenPerformSendMessage() throws IOException {
        final Issue issue = mock(Issue.class);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);
        doThrow(new IOException()).when(sender).send(any(Message.class), regIds, retries);
        gcmNotificationSender.send(issue);
    }*/


}
