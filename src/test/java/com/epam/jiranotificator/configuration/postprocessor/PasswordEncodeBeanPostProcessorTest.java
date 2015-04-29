package com.epam.jiranotificator.configuration.postprocessor;

import com.epam.jiranotificator.service.impl.JiraService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.config.BeanPostProcessor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PasswordEncodeBeanPostProcessorTest {

    private String TEST_LOGIN = "test";
    private String TEST_PASSWORD = "UXklcnR5MTIz";
    private String TEST_URL = "test";
    private String TEST_QUERY = "test";

    private String NULL_PASSWORD = "password='null'";

    private BeanPostProcessor beanPostProcessor = new PasswordEncodeAnnotationBeanPostProcessor();

    @Test
    public void shouldReturnTheSameObjectAfterPerformPostProcessAfterInitialization() throws Exception {
        final JiraService jiraService = mock(JiraService.class);
        Object actual = beanPostProcessor.postProcessAfterInitialization(jiraService, jiraService.getClass().getName());
        assertEquals("PostProcessAfterInitialization should return the same object", jiraService, actual);
    }

    @Test
    public void shouldDecodeAndUpdatePasswordWhenAnnotationIsPresent() throws Exception {
        final JiraService jiraService = new JiraService(TEST_LOGIN, TEST_URL, TEST_PASSWORD, TEST_QUERY);
        final JiraService jiraServiceActual = (JiraService) beanPostProcessor.postProcessBeforeInitialization(jiraService, jiraService.getClass().getName());
        final String actualToString = jiraServiceActual.toString();

        assertFalse("postProcessBeforeInitialization should inject decode password", actualToString.contains(NULL_PASSWORD));
    }

}
