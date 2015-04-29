package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.Issue;

public interface MemoryCacheService {

    void put(final String key, final Issue value);

    Issue get(final String key);
}
