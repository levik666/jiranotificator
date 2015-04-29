package com.epam.jiranotificator.configuration.context.event;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.springframework.context.ApplicationEvent;

public class AlertEvent extends ApplicationEvent {
    private final Issue issue;

    public AlertEvent(final Object source, final Issue issue) {
        super(source);
        this.issue = issue;
    }

    public Issue getIssue() {
        return issue;
    }
}
