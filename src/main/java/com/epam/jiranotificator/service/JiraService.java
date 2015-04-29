package com.epam.jiranotificator.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

/**
 * Jira Service
 * 
 * @author Bohdan_Kolesnyk
 *
 */
@Service
public class JiraService {

    @Value("${jira.login}")
    private String login;

    @Value("${jira.url}")
    private String url;

    @Autowired
    private CryptService cryptService;

    public JiraService(final String login, final String url) {
        this.login = login;
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
    public Set<Issue> getIssuesByQuery(final String searchQuery) throws IOException, URISyntaxException {
        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        final URI jiraServerUri = new URI(url);

        try(JiraRestClient restClient = factory.createWithBasicHttpAuthentication(
                jiraServerUri, login, cryptService.decode())){

            final Promise<SearchResult> searchResult = restClient.getSearchClient()
                    .searchJql(searchQuery);
            final Set<Issue> issues = new HashSet<>();

            for (Issue issue : searchResult.claim().getIssues()) {
                issues.add(issue);
            }

            return issues;
        }
    }

}
