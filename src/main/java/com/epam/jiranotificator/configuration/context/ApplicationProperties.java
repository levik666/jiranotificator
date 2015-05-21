package com.epam.jiranotificator.configuration.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

public class ApplicationProperties extends Properties {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationProperties.class);

    private static ApplicationProperties instance;

    private ApplicationProperties() {
    }


    public static final ApplicationProperties get() {
        if (instance == null){
            instance = new ApplicationProperties();
            instance.putAll(loadProperties());
        }

        return instance;
    }

    protected static Properties loadProperties() {
        LOG.debug("loadProperties");
        final Properties properties = new Properties();
        final Map<String, String> getenv = System.getenv();

        if (!getenv.isEmpty()){
            LOG.debug("getenv.isNotEmpty");
            for (final String key : getenv.keySet()) {
                final String value = getenv.get(key);
                LOG.debug("key " + key + " , value " + value);
                properties.put(key, value);
            }
        }

        LOG.debug("getenv.isNotEmpty");
        return properties;
    }

}
