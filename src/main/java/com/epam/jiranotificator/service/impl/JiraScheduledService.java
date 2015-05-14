package com.epam.jiranotificator.service.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.configuration.context.EventPublisher;
import com.epam.jiranotificator.service.MemoryCacheService;
import com.epam.jiranotificator.service.RoutingIssueService;
import com.epam.jiranotificator.service.ScheduledService;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class JiraScheduledService implements ScheduledService {

    private static final Logger LOG = LoggerFactory.getLogger(JiraScheduledService.class);

    @Autowired
    private JiraService jiraService;
    @Autowired
    private MemoryCacheService<Issue> issueCacheService;
    @Autowired
    private EventPublisher eventPublisher;
    @Autowired
    private RoutingIssueService routingJiraIssueService;

    @Override
    @Scheduled(cron = "${cron.expression}")
    public void perform(){
        final DateTime startTime = new DateTime();
        LOG.debug("Perform jiraScheduledService startTime : " + startTime);

        final Set<Issue> issues = jiraService.getIssues();
        for(final Issue issue : issues){
            final String ticketNumber = issue.getKey();
            //if (routingJiraIssueService.isSendNotification(issue)){
                LOG.debug("Issue [" + ticketNumber + "] found.");
                final Issue cacheIssue = issueCacheService.get(ticketNumber);
                if (cacheIssue == null){
                    eventPublisher.publish(issue);
                    issueCacheService.put(ticketNumber, issue);
                    LOG.debug("Publish event and add to cache issue [" + ticketNumber + "] ");
                    continue;
                }
                LOG.debug("Issue [" + ticketNumber + "] available in cache.");
            //}
            LOG.debug("Issue [" + ticketNumber + "] do nothing due to lover priority");
        }
        final DateTime endTime = new DateTime();
        final Period period = new Period(startTime, endTime);

        LOG.debug("JiraScheduledService completed endTime : " + endTime + " , time spend : " + PeriodFormat.getDefault().print(period));

    }
}
