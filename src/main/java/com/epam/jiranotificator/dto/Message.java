package com.epam.jiranotificator.dto;

import java.io.Serializable;

public class Message implements Serializable {

    //default 1 - android
    private int platform = 1;
    private String message;

    public Message(final String message) {
        this.message = message;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{ platform :" + platform + ", msg : " + message + "}";
    }
}
