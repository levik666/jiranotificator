package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.Issue;

public interface RoutingIssueService {

    boolean isSendNotification(final Issue issue);
}
