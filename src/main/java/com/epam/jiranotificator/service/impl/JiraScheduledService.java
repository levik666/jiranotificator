package com.epam.jiranotificator.service.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.configuration.context.EventPublisher;
import com.epam.jiranotificator.service.MemoryCacheService;
import com.epam.jiranotificator.service.RoutingIssueService;
import com.epam.jiranotificator.service.ScheduledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Set;

@Service
public class JiraScheduledService implements ScheduledService {

    private static final Logger LOG = LoggerFactory.getLogger(JiraScheduledService.class);

    @Autowired
    private JiraService jiraService;
    @Autowired
    private MemoryCacheService memoryCacheService;
    @Autowired
    private EventPublisher eventPublisher;
    @Autowired
    private RoutingIssueService routingJiraIssueService;

    @Override
    @Scheduled(cron = "${cron.expression}")
    public void perform(){
        LOG.debug("Perform jiraScheduledService by currentTime " + Calendar.getInstance().getTime());

        final Set<Issue> issues = jiraService.getIssues();
        for(final Issue issue : issues){
            final String ticketNumber = issue.getKey();
            if (routingJiraIssueService.isSendNotification(issue)){
                LOG.debug("Issue [" + ticketNumber + "] found.");
                final Issue cacheIssue = memoryCacheService.get(ticketNumber);
                if (cacheIssue == null){
                    eventPublisher.publish(issue);
                    memoryCacheService.put(ticketNumber, issue);
                    LOG.debug("Publish event and add to cache issue [" + ticketNumber + "] ");
                    continue;
                }
                LOG.debug("Issue [" + ticketNumber + "] available in cache.");
            }
            LOG.debug("Issue [" + ticketNumber + "] do nothing due to lover priority");
        }

    }
}
