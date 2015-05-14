package com.epam.jiranotificator.service.impl;

import com.epam.jiranotificator.service.MemoryCacheService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class EmailCacheService implements MemoryCacheService<String> {

    private LoadingCache<String, String> cache;

    public EmailCacheService(final long duration,final TimeUnit unit, final long size){
        cache = CacheBuilder.newBuilder().expireAfterWrite(duration, unit)
                .maximumSize(size)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String queryKey) throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public void put(final String key, final String value){
        cache.put(key, value);
    }

    /**
     *
     * @param key - jira ticket number
     * @return Issue if present in other cases case null
     */
    @Override
    public String get(final String key){
        try{
            return cache.get(key);
        }catch (ExecutionException | CacheLoader.InvalidCacheLoadException exe){
            return null;
        }
    }
}
