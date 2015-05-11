package com.epam.jiranotificator.service.impl;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.dto.Message;
import com.epam.jiranotificator.exception.GCMException;
import com.epam.jiranotificator.service.GCMSender;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("gcmNotificationSender")
public class GCMNotificationSender implements GCMSender {

    private static final Logger LOG = LoggerFactory.getLogger(GCMNotificationSender.class);

    private static final String X_PUSHBOTS_APPID = "X-PUSHBOTS-APPID";
    private static final String X_PUSHBOTS_SECRET = "X-PUSHBOTS-SECRET";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";
    private static final String MESSAGE = "message";
    public static final String POINT = "] ->";
    public static final String COMA = ",";
    public static final String BRACKAT = "[";

    private final String host;
    private final String contentType;
    private final String xPushbotsAppid;
    private final String xPushbotsSecret;

    @Autowired
    private HttpClient httpClient;


    public GCMNotificationSender(final String host, final String contentType, final String xPushbotsAppid, final String xPushbotsSecret) {
        this.host = host;
        this.contentType = contentType;
        this.xPushbotsAppid = xPushbotsAppid;
        this.xPushbotsSecret = xPushbotsSecret;
    }

    @Override
    public void send(final Issue issue) {
        HttpPost post = null;
        LOG.debug("initialized message with issue " + issue.getKey());
        final Message message = new Message(messageHelper(issue));
        LOG.debug("message is  " + message.toString());

        try{
            post = new HttpPost(host);

            post.setHeader(X_PUSHBOTS_APPID, xPushbotsAppid);
            post.setHeader(X_PUSHBOTS_SECRET, xPushbotsSecret);
            post.setHeader(CONTENT_TYPE, contentType);

            try {
                final String jsonMessage = new ObjectMapper().writeValueAsString(message);
                post.setEntity(new StringEntity(jsonMessage, ContentType.create(contentType)));
                final HttpResponse response = httpClient.execute(post);

                if (response.getStatusLine().getStatusCode() != 200){
                    LOG.error("Can't send message, response " + response.toString() + " , to issue " + issue.getKey());
                    return ;
                }

                LOG.debug("send message to gcm, result is " + response.toString());
            } catch (IOException exe) {
                final String errorMessage = "Can't send gcm message due to error " + exe.getMessage();
                LOG.debug(errorMessage);
                throw new GCMException(errorMessage, exe);
            }
        }finally {
            if (post != null){
                post.releaseConnection();
            }
        }
    }

    private String messageHelper(final Issue issue) {
        final BasicPriority priority = issue.getPriority();
        if (priority != null) {
            return BRACKAT + issue.getKey() + COMA + priority.getName() + POINT + issue.getSummary();
        }
        return BRACKAT + issue.getKey() + POINT + issue.getSummary();
    }
}
