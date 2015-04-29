package com.epam.jiranotificator.service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.service.impl.IssueCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class IssueCacheServiceTest {

    @Mock
    private Issue issue;

    private static final String TEST_KEY = "1";
    private static final String TEST_KEY_THREE = "3";
    private static final String TEST_NOT_ADDED_KEY = "7";

    private static final long DURATION_MILLISECONDS = 10000;

    private static final long DURATION = 10;
    private static final long SIZE = 5;
    private static final TimeUnit TIME_UNIT_SECONDS = TimeUnit.SECONDS;

    private MemoryCacheService memoryCacheService = new IssueCacheService(DURATION, TIME_UNIT_SECONDS, SIZE);

    @Test
    public void shouldSuccessfulUseDefaultContractorPutDataToCacheAndGetIt() {
        final MemoryCacheService memoryCacheService = new IssueCacheService();
        memoryCacheService.put(TEST_KEY, issue);

        for (int i = 0; i <= 5; i++) {
            final Issue actualIssue = memoryCacheService.get(TEST_KEY);
            assertEquals("Should get data from cache", issue, actualIssue);
        }
    }

    @Test
    public void shouldAddMoreElementThanSizeAndTryToPerformNotAddedKey() {
        final MemoryCacheService memoryCacheService = new IssueCacheService();
        for(int i = 0; i <= 6; i++){
            memoryCacheService.put(""+i, issue);
        }

        for (int i = 0; i <= 5; i++) {
            final Issue actualIssue = memoryCacheService.get(TEST_NOT_ADDED_KEY);
            assertNull("Should return null due to cache is released", actualIssue);
        }
    }

    @Test
    public void shouldAddMoreElementThanSizeTryToPerformTheThreeKey() {
        final MemoryCacheService memoryCacheService = new IssueCacheService();
        for(int i = 0; i <= 6; i++){
            memoryCacheService.put(""+i, issue);
        }

        for (int i = 0; i <= 5; i++) {
            final Issue actualIssue = memoryCacheService.get(TEST_KEY_THREE);
            assertEquals("Should get data from cache", issue, actualIssue);
        }
    }

    @Test
    public void shouldSuccessPutDataToCacheAndGetIt() {
        memoryCacheService.put(TEST_KEY, issue);

        for (int i = 0; i <= 5; i++) {
            final Issue actualIssue = memoryCacheService.get(TEST_KEY);
            assertEquals("Should get data from cache", issue, actualIssue);
        }
    }

    @Test
    public void shouldReturnNullDueToCacheIsEmpty() throws InterruptedException {
        memoryCacheService.put(TEST_KEY, issue);

        Thread.currentThread().sleep(DURATION_MILLISECONDS);

        for (int i = 0; i <= 5; i++) {
            final Issue actualIssue = memoryCacheService.get(TEST_KEY);
            assertNull("Should return null due to cache is released", actualIssue);
        }
    }

    @Test
    public void shouldReturnNullWhenKeyNotFoundInCache() throws ExecutionException {
        final Issue actualIssue = memoryCacheService.get(TEST_KEY);
        assertNull("Should return null due to cache is released", actualIssue);
    }

}
