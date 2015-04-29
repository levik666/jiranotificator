package com.epam.jiranotificator.service.impl;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.service.RoutingIssueService;
import com.epam.jiranotificator.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutingJiraIssueService implements RoutingIssueService {

    private final List<String> listForSendNotification;

    public RoutingJiraIssueService(final String status) {
        listForSendNotification = StringUtils.splitToList(status);
    }

    @Override
    public boolean isSendNotification(final Issue issue) {
        final BasicPriority basicPriority = issue.getPriority();
        return basicPriority != null && listForSendNotification.contains(basicPriority.getName());
    }
}
