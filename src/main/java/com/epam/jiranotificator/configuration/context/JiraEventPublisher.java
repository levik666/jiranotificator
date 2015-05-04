/**
 * 
 */
package com.epam.jiranotificator.configuration.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.epam.jiranotificator.configuration.context.event.JiraConnectionAlertEvent;

/**
 * Jira event publisher
 * 
 * @author Bohdan_Kolesnyk
 *
 */
@Configurable
@Component
public class JiraEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(final String subject, final String text) {
        applicationEventPublisher.publishEvent(new JiraConnectionAlertEvent(
                this, subject, text));
    }
}
