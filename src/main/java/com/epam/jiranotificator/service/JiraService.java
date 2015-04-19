package com.epam.jiranotificator.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

/**
 * Jira utils
 * 
 * @author Bohdan_Kolesnyk
 *
 */
@Configuration
@PropertySource("classpath:jira.properties")
@Service
public class JiraService {

    @Value("${login}")
    private String login;

    @Value("${password}")
    private String password;

    @Value("${url}")
    private String url;

    public JiraService(String login, String password, String url) {
        this.login = login;
        this.password = password;
        this.url = url;
    }

    public JiraService() {
    }

    /**
     * Get set of issues object from Jira by query
     * 
     * @throws URISyntaxException
     * @throws IOException
     */
    public Set<Issue> getIssuesByQuery(String searchQuery)
            throws URISyntaxException, IOException {
        Set<Issue> issues = new HashSet<Issue>();
        AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        URI jiraServerUri = new URI(url);

        JiraRestClient restClient = factory.createWithBasicHttpAuthentication(
                jiraServerUri, login, password);
        final Promise<SearchResult> searchResalt = restClient.getSearchClient()
                .searchJql(searchQuery);
        Iterator<Issue> issueIterator = searchResalt.claim().getIssues()
                .iterator();
        while (issueIterator.hasNext()) {
            issues.add(issueIterator.next());
        }
//        restClient.close();
        return issues;
    }

}
