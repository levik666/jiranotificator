package com.epam.jiranotificator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    private Application application = new Application();

    @Test
    public void shouldSuccessfulPerformApplication() {
        String[] args = new String[]{};
        application.main(args);
    }
}
