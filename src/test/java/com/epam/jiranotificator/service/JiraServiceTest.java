package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.exception.JiraException;
import com.epam.jiranotificator.service.impl.JiraService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JiraServiceTest {

    private String NULL_PASSWORD = "password='null'";
    private String TEST_URL = "test_url";
    private String TEST_LOGIN = "test_login";
    private String TEST_PASSWORD = "test_password";
    private String TEST_QUERY = "test_query";

    @Mock
    private JiraService jiraService;

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Test
    public void shouldSuccessPerformIssuesByQuery() throws Exception {
        final JiraService jiraService = applicationContext.getBean(JiraService.class);
        final Set<Issue> issues = jiraService.getIssues();
        assertNotNull("Issues result can't be null", issues);
    }

    @Test(expected = JiraException.class)
    public void shouldThrowExceptionWhenPerformIssuesByQuery() throws Exception {
        when(jiraService.getIssues()).thenThrow(new JiraException("Can't perform query"));
        jiraService.getIssues();
    }

    @Test(expected = JiraException.class)
    public void shouldThrowExceptionWithStackTraceWhenPerformIssuesByQuery() throws Exception {
        when(jiraService.getIssues()).thenThrow(new JiraException("Can't perform query", new Throwable()));
        jiraService.getIssues();
    }

    @Test
    public void shouldReadJiraPropertyFileAndVerifyParameters(){
        final JiraService jiraService = applicationContext.getBean(JiraService.class);
        final String actual = jiraService.toString();
        assertFalse(actual.contains(NULL_PASSWORD));
    }
}
