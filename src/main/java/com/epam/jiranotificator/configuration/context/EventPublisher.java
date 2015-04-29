package com.epam.jiranotificator.configuration.context;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.configuration.context.event.AlertEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(final Issue issue){
        applicationEventPublisher.publishEvent(new AlertEvent(this, issue));
    }
}
