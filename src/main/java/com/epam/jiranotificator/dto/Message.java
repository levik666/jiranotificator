package com.epam.jiranotificator.dto;

import java.io.Serializable;

public class Message implements Serializable {

    //default 1 - android
    private int platform = 1;
    private String msg;

    public Message(final String msg) {
        this.msg = msg;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{ platform :" + platform + ", msg : " + msg + "}";
    }
}
