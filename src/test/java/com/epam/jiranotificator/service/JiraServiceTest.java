package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class JiraServiceTest {

    private JiraService jiraService;

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Test
    public void shouldSuccessPerformIssuesByQuery() throws Exception {
        jiraService = applicationContext.getBean(JiraService.class);

        final String query = "project = CTCODCQCT AND labels = Support_Investigate AND labels not in (Triaged) AND status in (Open, \"In Progress\", Reopened)";
        final Set<Issue> issues = jiraService.getIssuesByQuery(query);
        assertNotNull("Issues result can't be null", issues);
    }
}
