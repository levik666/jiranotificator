package com.epam.jiranotificator.configuration.context.event;

import org.springframework.context.ApplicationEvent;

/**
 * Jira connection alert event
 * 
 * @author Bohdan_Kolesnyk
 *
 */
public class JiraConnectionAlertEvent extends ApplicationEvent {
    /**
     * 
     */
    private static final long serialVersionUID = -1890305162149288527L;

    private String subject;
    private String text;

    public JiraConnectionAlertEvent(Object source, String subject, String text) {
        super(source);
        this.subject = subject;
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
