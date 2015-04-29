package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.service.impl.RoutingJiraIssueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoutingJiraIssueServiceTest {

    private static final String STATUS = "Critical,Blocker";

    private final String CRITICAL_PRIORITY_NAME = "Critical";
    private final String MAJOR_PRIORITY_NAME = "MAJOR";
    private final String BLOCKER_PRIORITY_NAME = "Blocker";

    private final String TICKET_NUMBER = "test-1234";

    private RoutingJiraIssueService routingJiraIssueService = new RoutingJiraIssueService(STATUS);

    @Test
    public void shouldSuccessfulReturnTrueForCriticalIssue(){
        final Issue issue = mock(Issue.class);
        final BasicPriority priority = mock(BasicPriority.class);

        when(priority.getName()).thenReturn(CRITICAL_PRIORITY_NAME);
        when(issue.getPriority()).thenReturn(priority);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);

        boolean actual = routingJiraIssueService.isSendNotification(issue);
        assertTrue("Should return true for critical issue", actual);
    }

    @Test
    public void shouldSuccessfulReturnTrueForBlockerIssue(){
        final Issue issue = mock(Issue.class);
        final BasicPriority priority = mock(BasicPriority.class);

        when(priority.getName()).thenReturn(BLOCKER_PRIORITY_NAME);
        when(issue.getPriority()).thenReturn(priority);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);

        boolean actual = routingJiraIssueService.isSendNotification(issue);
        assertTrue("Should return true for blocker issue", actual);
    }

    @Test
    public void shouldSuccessfulReturnFalseForMajorIssue(){
        final Issue issue = mock(Issue.class);
        final BasicPriority priority = mock(BasicPriority.class);

        when(priority.getName()).thenReturn(MAJOR_PRIORITY_NAME);
        when(issue.getPriority()).thenReturn(priority);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);

        boolean actual = routingJiraIssueService.isSendNotification(issue);
        assertFalse("Should return false for major issue",actual);
    }
}
