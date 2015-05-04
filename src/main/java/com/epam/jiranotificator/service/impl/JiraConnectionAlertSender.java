/**
 * 
 */
package com.epam.jiranotificator.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.epam.jiranotificator.configuration.context.event.JiraConnectionAlertEvent;

/**
 * Jira connection alert sender
 * 
 * @author Bohdan_Kolesnyk
 *
 */
@Service
public class JiraConnectionAlertSender implements
        ApplicationListener<JiraConnectionAlertEvent> {

    private static final Logger LOG = LoggerFactory
            .getLogger(JiraConnectionAlertSender.class);

    @Autowired
    private EmailSender emailSender;

    @Override
    public void onApplicationEvent(final JiraConnectionAlertEvent event) {
        LOG.debug("Jira connection alert, event " + event);
        emailSender.send(event.getSubject(), event.getText());
    }
}
