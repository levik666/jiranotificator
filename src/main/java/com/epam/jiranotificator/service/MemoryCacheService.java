package com.epam.jiranotificator.service;

public interface MemoryCacheService<T> {

    void put(final String key, final T value);

    T get(final String key);
}
