package com.epam.jiranotificator.exception;

public class GCMException extends RuntimeException{

    public GCMException(String message, Throwable cause) {
        super(message, cause);
    }

    public GCMException(String message) {
        super(message);
    }
}
