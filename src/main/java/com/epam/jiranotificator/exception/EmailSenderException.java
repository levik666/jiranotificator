/**
 * 
 */
package com.epam.jiranotificator.exception;

/**
 * @author Bohdan_Kolesnyk
 *
 */
public class EmailSenderException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -4136231962247110749L;

    public EmailSenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailSenderException(String message) {
        super(message);
    }
}
