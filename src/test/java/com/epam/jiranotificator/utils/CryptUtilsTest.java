package com.epam.jiranotificator.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CryptUtilsTest {

    private static final String CURRENT_PASSWORD = "Qwerty123";
    private static final String ENCODE_PASSWORD = "UXdlcnR5MTIz";

    private static final String INCORRECT_ENCODE_PASSWORD = "UXklcnR5MTIz";

    @Test
    public void shouldSuccessPerformEncodeAndValidatePassword() throws Exception {
        final String encode = CryptUtils.encode(CURRENT_PASSWORD);
        assertTrue("Input Password should be valid", ENCODE_PASSWORD.equals(encode));
    }

    @Test
    public void shouldSuccessPerformEncodeAndItNotBeNull() throws Exception {
        final String encode = CryptUtils.encode(CURRENT_PASSWORD);
        assertNotNull("Encode password should be not null", encode);
    }

    @Test
    public void shouldPerformEncodeAndValidationPasswordFailed() throws Exception {
        final String encode = CryptUtils.encode(CURRENT_PASSWORD);
        assertFalse("Input Password should be failed due to incorrect password", INCORRECT_ENCODE_PASSWORD.equals(encode));
    }

    @Test
    public void shouldSuccessPerformDecodeAndValidatePassword() throws Exception {
        final String decode = CryptUtils.decode(ENCODE_PASSWORD);
        assertTrue("Input decode Password should be valid", CURRENT_PASSWORD.equals(decode));
    }

    @Test
    public void shouldSuccessPerformDecodeAndItNotBeNull() throws Exception {
        final String decode = CryptUtils.decode(ENCODE_PASSWORD);
        assertNotNull("Decode password should be not null", decode);
    }

    @Test
    public void shouldPerformDecodeAndValidationPasswordFailed() throws Exception {
        final String decode = CryptUtils.decode(INCORRECT_ENCODE_PASSWORD);
        assertFalse("Input Password should be failed due to incorrect password", CURRENT_PASSWORD.equals(decode));
    }

}
