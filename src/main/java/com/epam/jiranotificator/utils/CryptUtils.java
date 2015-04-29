package com.epam.jiranotificator.utils;

import org.apache.commons.codec.binary.Base64;

public final class CryptUtils {

    public static String encode(final String text) {
        return new String(Base64.encodeBase64(text.getBytes()));
    }

    public static String decode(final String text){
        return new String(Base64.decodeBase64(text));
    }
}
