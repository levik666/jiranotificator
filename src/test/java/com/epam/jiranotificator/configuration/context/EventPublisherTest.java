package com.epam.jiranotificator.configuration.context;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventPublisherTest {

    @Mock
    private Issue issue;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Spy
    @InjectMocks
    private EventPublisher eventPublisher = new EventPublisher();

    @Test
    public void shouldSuccessPublishEvent() {
        eventPublisher.publish(issue);
        verify(applicationEventPublisher).publishEvent((ApplicationEvent) Matchers.any());
    }
}
