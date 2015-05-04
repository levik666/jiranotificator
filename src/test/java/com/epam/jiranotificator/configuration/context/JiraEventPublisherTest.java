/**
 * 
 */
package com.epam.jiranotificator.configuration.context;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author Bohdan_Kolesnyk
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JiraEventPublisherTest {
    
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Spy
    @InjectMocks
    private JiraEventPublisher jiraEventPublisher = new JiraEventPublisher();

    @Test
    public void shouldSuccessPublishEvent() {
        jiraEventPublisher.publish("Test subject", "Test text");
        verify(applicationEventPublisher).publishEvent((ApplicationEvent) Matchers.any());
    }
}
