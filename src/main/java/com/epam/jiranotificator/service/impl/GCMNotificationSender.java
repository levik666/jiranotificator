package com.epam.jiranotificator.service.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.exception.GCMException;
import com.epam.jiranotificator.service.GCMSender;
import com.epam.jiranotificator.utils.StringUtils;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("gcmNotificationSender")
public class GCMNotificationSender implements GCMSender {

    private static final Logger LOG = LoggerFactory.getLogger(GCMNotificationSender.class);

    @Autowired
    @Qualifier("gcmSender")
    private Sender sender;

    private static final String MESSAGE_KEY = "message";

    private final List<String> regIds;
    private final int timeToLive;
    private final int retries;

    public GCMNotificationSender(final String regIds, final int timeToLive, final int retries) {
        this.timeToLive = timeToLive;
        this.retries = retries;
        this.regIds = StringUtils.splitToList(regIds);
    }

    @Override
    public void send(final Issue issue) {
        LOG.debug("initialized message with issue " + issue.getKey());
        final String messageText = messageHelper(issue);
        LOG.debug("message is  " + messageText);
        final Message message = new Message.Builder().timeToLive(timeToLive)
                .delayWhileIdle(true).addData(MESSAGE_KEY, messageText).build();
        try {

            final MulticastResult result = sender.send(message, regIds, retries);

            LOG.debug("send message to gcm, result is " + result);
        } catch (IOException exe) {
            final String errorMessage = "Can't send gcm message due to error " + exe.getMessage();
            LOG.debug(errorMessage);
            throw new GCMException(errorMessage, exe);
        }

    }

    private String messageHelper(final Issue issue){
        return "Hello!!!";
    }
}
