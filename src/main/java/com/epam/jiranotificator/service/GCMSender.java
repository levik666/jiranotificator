package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.Issue;

public interface GCMSender {

    void send(final Issue issue);
}
