package com.epam.jiranotificator.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class CryptService {

    private String password;
    private String salt;

    public CryptService(final String password, final String salt) {
        this.password = password;
        this.salt = salt;
    }

    public String encrypted() {
        final String hashed = generateMD5Hash(password);
        return generateMD5Hash(hashed + salt);
    }

    public boolean isPasswordValid(final String password, final String encryptedPassword){
        final String newEncryptedPassword = encryptPassword(password, salt);
        return newEncryptedPassword.equals(encryptedPassword);
    }

    private String encryptPassword(final String oldPassword, final String salt){
        String hashedPassword = generateMD5Hash(oldPassword);
        return generateMD5Hash(hashedPassword + salt);
    }

    private String generateMD5Hash(final String text){
        return DigestUtils.md5Hex(text);
    }
}
