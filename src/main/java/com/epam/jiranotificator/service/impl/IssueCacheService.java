package com.epam.jiranotificator.service.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.service.MemoryCacheService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class IssueCacheService implements MemoryCacheService {

    private static final long DURATION = 30;
    private static final long SIZE = 5;
    private static final TimeUnit TIME_UNIT_MINUTES = TimeUnit.MINUTES;

    private LoadingCache<String, Issue> cache;

    public IssueCacheService(){
        cache = CacheBuilder.newBuilder().expireAfterWrite(DURATION, TIME_UNIT_MINUTES)
                .maximumSize(SIZE)
                .build(new CacheLoader<String, Issue>() {
                    @Override
                    public Issue load(String queryKey) throws Exception {
                        return null;
                    }
                });
    }

    public IssueCacheService(final long duration,final TimeUnit unit, final long size){
        cache = CacheBuilder.newBuilder().expireAfterWrite(duration, unit)
                .maximumSize(size)
                .build(new CacheLoader<String, Issue>() {
                    @Override
                    public Issue load(String queryKey) throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public void put(final String key, final Issue value){
        cache.put(key, value);
    }

    /**
     *
     * @param key - jira ticket number
     * @return Issue if present in other cases case null
     */
    @Override
    public Issue get(final String key){
        try{
            return cache.get(key);
        }catch (ExecutionException | CacheLoader.InvalidCacheLoadException exe){
            return null;
        }
    }
}
