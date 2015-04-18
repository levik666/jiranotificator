package com.epam.jiranotificator.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CryptServiceTest {

    private static final String TEST_SALT = "test_salt";
    private static final String CURRENT_PASSWORD = "Qwerty123";

    private CryptService cryptService;

    @Before
    public void setUp() {
        cryptService = new CryptService(CURRENT_PASSWORD, TEST_SALT);
    }

    @Test
    public void shouldSuccessPerformEncrypedAndValidatePassword() throws Exception {
        final String inputPassword = "Qwerty123";
        final String encrypted = cryptService.encrypted();
        boolean isPasswordValid = cryptService.isPasswordValid(inputPassword, encrypted);
        assertTrue("Input Password should be valid",isPasswordValid);
    }

    @Test
    public void shouldSuccessPerformEncrypedAndItNotBeNull() throws Exception {
        final String encrypted = cryptService.encrypted();
        assertNotNull("Encrypted password should be not null", encrypted);
    }

    @Test
    public void shouldPerformEncrypedAndValidationPasswordFailed() throws Exception {
        final String inputPassword = "Qwerty12";
        final String encrypted = cryptService.encrypted();
        boolean isPasswordValid = cryptService.isPasswordValid(inputPassword, encrypted);
        assertFalse("Input Password should be failed due to incorect password", isPasswordValid);
    }
}
