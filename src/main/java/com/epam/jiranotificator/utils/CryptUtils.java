package com.epam.jiranotificator.utils;

import org.apache.commons.codec.binary.Base64;

public final class CryptUtils {
    private static final String SALT = "Ctco-Ecom_Epam_2015";
    private static final String SALT_HASH = new String(Base64.encodeBase64(SALT.getBytes()));
    private static final String EMPTY = "";

    public static String encode(final String text) {
        final String password = SALT_HASH + SALT.hashCode() + text;
        return new String(Base64.encodeBase64(password.getBytes()));
    }

    public static String decode(final String text){
        final String password = new String(Base64.decodeBase64(text));
        return password.replace(SALT_HASH + SALT.hashCode(), EMPTY);
    }
}
