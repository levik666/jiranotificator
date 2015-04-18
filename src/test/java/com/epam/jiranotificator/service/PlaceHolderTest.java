package com.epam.jiranotificator.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PlaceHolderTest {

    private static final String INPUT_PASSWORD = "Qwerty123";

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Test
    public void shouldReadJiraPropertyFileAndVerifyParameters(){
        final CryptService cryptService = applicationContext.getBean(CryptService.class);
        final String encrypted = cryptService.encrypted();

        boolean isPasswordValid = cryptService.isPasswordValid(INPUT_PASSWORD, encrypted);
        assertTrue("Input Password should be valid",isPasswordValid);
    }



}
