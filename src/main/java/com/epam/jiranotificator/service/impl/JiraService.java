package com.epam.jiranotificator.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import com.epam.jiranotificator.configuration.annotations.PasswordEncode;
import com.epam.jiranotificator.configuration.annotations.AlertNotification;
import com.epam.jiranotificator.exception.JiraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

@Service("jiraService")
public class JiraService {

    private final String login;
    private final String url;
    private String query;
    @PasswordEncode
    private final String password;

    @Autowired
    private AsynchronousJiraRestClientFactory jiraRestClientFactory;

    public JiraService(final String login, final String url, final String password, final String query) {
        this.login = login;
        this.url = url;
        this.password = password;
        this.query = query;
    }

    @AlertNotification
    public Set<Issue> getIssues() {
        try{
            final URI jiraServerUri = new URI(url);
            try(JiraRestClient restClient = jiraRestClientFactory.createWithBasicHttpAuthentication(
                    jiraServerUri, login, password)){

                final Promise<SearchResult> searchResult = restClient.getSearchClient().searchJql(query);
                final Set<Issue> issues = new HashSet<>();

                for (Issue issue : searchResult.claim().getIssues()) {
                    issues.add(issue);
                }

                return issues;
            }
        } catch (IOException | URISyntaxException exe) {
            throw new JiraException("Can't perform query " + query + ". Get error message " + exe.getMessage(), exe);
        }
    }

    @Override
    public String toString() {
        return "JiraService{" +
                "login='" + login + '\'' +
                ", url='" + url + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
