package com.epam.jiranotificator.service.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.configuration.context.event.AlertEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class AlertPushNotificationSender implements ApplicationListener<AlertEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(GCMNotificationSender.class);

    @Autowired
    private GCMNotificationSender gcmNotificationSender;

    @Override
    public void onApplicationEvent(final AlertEvent event) {
        LOG.debug("Alert push notification to gcm, event " + event);
        final Issue issue = event.getIssue();
        gcmNotificationSender.send(issue);
    }
}
