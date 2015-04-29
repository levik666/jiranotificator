package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.epam.jiranotificator.configuration.context.EventPublisher;
import com.epam.jiranotificator.service.impl.JiraScheduledService;
import com.epam.jiranotificator.service.impl.JiraService;
import com.epam.jiranotificator.service.impl.RoutingJiraIssueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JiraScheduledServiceTest {
    private final String CRITICAL_PRIORITY_NAME = "Critical";
    private final String MAJOR_PRIORITY_NAME = "MAJOR";
    private final String BLOCKER_PRIORITY_NAME = "Blocker";

    private final String TICKET_NUMBER = "test-1234";
    private final String TICKET_NUMBER_1 = "test-1231";
    private final String TICKET_NUMBER_2 = "test-1232";
    private final String TICKET_NUMBER_3 = "test-1233";

    @Mock
    private MemoryCacheService memoryCacheService;
    @Mock
    private RoutingIssueService routingJiraIssueService;
    @Mock
    private EventPublisher eventPublisher;
    @Mock
    private JiraService jiraService;
    @Spy
    @InjectMocks
    private JiraScheduledService jiraScheduledService = new JiraScheduledService();

    @Test
    public void shouldSuccessfulPerformScheduler(){
        final Set<Issue> issues = new HashSet<>();

        final Issue issue = mock(Issue.class);
        final BasicPriority priority = mock(BasicPriority.class);

        when(priority.getName()).thenReturn(CRITICAL_PRIORITY_NAME);
        when(issue.getPriority()).thenReturn(priority);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);

        issues.add(issue);

        when(routingJiraIssueService.isSendNotification(issue)).thenReturn(true);

        when(jiraService.getIssues()).thenReturn(issues);

        jiraScheduledService.perform();

        verify(eventPublisher).publish(issue);
        verify(memoryCacheService).put(issue.getKey(), issue);
    }

    @Test
    public void shouldSuccessfulPerformSchedulerWithMajorIssue(){
        final Set<Issue> issues = new HashSet<>();

        final Issue issue = mock(Issue.class);
        final BasicPriority priority = mock(BasicPriority.class);

        when(priority.getName()).thenReturn(MAJOR_PRIORITY_NAME);
        when(issue.getPriority()).thenReturn(priority);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);

        issues.add(issue);

        when(jiraService.getIssues()).thenReturn(issues);

        jiraScheduledService.perform();

        verify(eventPublisher, never()).publish(issue);
        verify(memoryCacheService, never()).put(issue.getKey(), issue);
    }

    @Test
    public void shouldSuccessfulPerformSchedulerWithBlockerIssue(){
        final Set<Issue> issues = new HashSet<>();

        final Issue issue = mock(Issue.class);
        final BasicPriority priority = mock(BasicPriority.class);

        when(priority.getName()).thenReturn(BLOCKER_PRIORITY_NAME);
        when(issue.getPriority()).thenReturn(priority);
        when(issue.getKey()).thenReturn(TICKET_NUMBER);

        issues.add(issue);

        when(routingJiraIssueService.isSendNotification(issue)).thenReturn(true);
        when(jiraService.getIssues()).thenReturn(issues);

        jiraScheduledService.perform();

        verify(eventPublisher).publish(issue);
        verify(memoryCacheService).put(issue.getKey(), issue);
    }

    @Test
    public void shouldSuccessfulPerformSchedulerWithTheeTypeOfIssues(){
        final Set<Issue> issues = new HashSet<>();

        final Issue issueBlocker = mock(Issue.class);
        final BasicPriority priorityBlocker = mock(BasicPriority.class);

        when(priorityBlocker.getName()).thenReturn(BLOCKER_PRIORITY_NAME);
        when(issueBlocker.getPriority()).thenReturn(priorityBlocker);
        when(issueBlocker.getKey()).thenReturn(TICKET_NUMBER_1);

        final Issue issueMajor = mock(Issue.class);
        final BasicPriority priorityMajor = mock(BasicPriority.class);

        when(priorityMajor.getName()).thenReturn(MAJOR_PRIORITY_NAME);
        when(issueMajor.getPriority()).thenReturn(priorityMajor);
        when(issueMajor.getKey()).thenReturn(TICKET_NUMBER_2);

        final Issue issueCritical = mock(Issue.class);
        final BasicPriority priorityCritical = mock(BasicPriority.class);

        when(priorityCritical.getName()).thenReturn(CRITICAL_PRIORITY_NAME);
        when(issueCritical.getPriority()).thenReturn(priorityCritical);
        when(issueCritical.getKey()).thenReturn(TICKET_NUMBER_3);

        issues.add(issueBlocker);
        issues.add(issueCritical);
        issues.add(issueMajor);

        when(routingJiraIssueService.isSendNotification(issueBlocker)).thenReturn(true);
        when(routingJiraIssueService.isSendNotification(issueCritical)).thenReturn(true);
        when(routingJiraIssueService.isSendNotification(issueMajor)).thenReturn(false);
        when(jiraService.getIssues()).thenReturn(issues);

        jiraScheduledService.perform();

        verify(eventPublisher).publish(issueBlocker);
        verify(memoryCacheService).put(issueBlocker.getKey(), issueBlocker);
        verify(eventPublisher).publish(issueCritical);
        verify(memoryCacheService).put(issueCritical.getKey(), issueCritical);
        verify(eventPublisher, never()).publish(issueMajor);
        verify(memoryCacheService, never()).put(issueMajor.getKey(), issueMajor);
    }

    @Test
    public void shouldSuccessfulPerformSchedulerWithTheeTypeOfIssuesButCacheContainsThisIssue(){
        final Set<Issue> issues = new HashSet<>();

        final Issue issueBlocker = mock(Issue.class);
        final BasicPriority priorityBlocker = mock(BasicPriority.class);

        when(priorityBlocker.getName()).thenReturn(BLOCKER_PRIORITY_NAME);
        when(issueBlocker.getPriority()).thenReturn(priorityBlocker);
        when(issueBlocker.getKey()).thenReturn(TICKET_NUMBER_1);

        final Issue issueMajor = mock(Issue.class);
        final BasicPriority priorityMajor = mock(BasicPriority.class);

        when(priorityMajor.getName()).thenReturn(MAJOR_PRIORITY_NAME);
        when(issueMajor.getPriority()).thenReturn(priorityMajor);
        when(issueMajor.getKey()).thenReturn(TICKET_NUMBER_2);

        final Issue issueCritical = mock(Issue.class);
        final BasicPriority priorityCritical = mock(BasicPriority.class);

        when(priorityCritical.getName()).thenReturn(CRITICAL_PRIORITY_NAME);
        when(issueCritical.getPriority()).thenReturn(priorityCritical);
        when(issueCritical.getKey()).thenReturn(TICKET_NUMBER_3);

        issues.add(issueBlocker);
        issues.add(issueCritical);
        issues.add(issueMajor);

        when(jiraService.getIssues()).thenReturn(issues);
        when(memoryCacheService.get(issueBlocker.getKey())).thenReturn(issueBlocker);
        when(memoryCacheService.get(issueCritical.getKey())).thenReturn(issueCritical);

        jiraScheduledService.perform();

        verify(eventPublisher, never()).publish(issueBlocker);
        verify(memoryCacheService, never()).put(issueBlocker.getKey(), issueBlocker);
        verify(eventPublisher, never()).publish(issueCritical);
        verify(memoryCacheService, never()).put(issueCritical.getKey(), issueCritical);
        verify(eventPublisher, never()).publish(issueMajor);
        verify(memoryCacheService, never()).put(issueMajor.getKey(), issueMajor);
    }
}
