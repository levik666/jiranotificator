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

    private static final String CURRENT_PASSWORD = "Qwerty123";
    private static final String ENCODE_PASSWORD = "UXdlcnR5MTIz";

    private static final String INCORRECT_ENCODE_PASSWORD = "UXklcnR5MTIz";

    private CryptService cryptService;

    @Before
    public void setUp() {
        cryptService = new CryptService(CURRENT_PASSWORD);
    }

    @Test
    public void shouldSuccessPerformEncodeAndValidatePassword() throws Exception {
        String encode = cryptService.encode();
        assertTrue("Input Password should be valid", ENCODE_PASSWORD.equals(encode));
    }

    @Test
    public void shouldSuccessPerformEncodeAndItNotBeNull() throws Exception {
        String encode = cryptService.encode();
        assertNotNull("Encode password should be not null", encode);
    }

    @Test
    public void shouldPerformEncodeAndValidationPasswordFailed() throws Exception {
        String encode = cryptService.encode();
        assertFalse("Input Password should be failed due to incorect password", INCORRECT_ENCODE_PASSWORD.equals(encode));
    }
}
