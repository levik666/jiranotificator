package com.epam.jiranotificator.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class CryptService {

    private String password;

    public CryptService(final String password) {
        this.password = password;
    }

    public String encode() {
        return new String(Base64.encodeBase64(password.getBytes()));
    }

    public String decode(){
        return new String(Base64.decodeBase64(password));
    }

}
