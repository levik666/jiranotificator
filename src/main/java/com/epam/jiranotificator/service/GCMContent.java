package com.epam.jiranotificator.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Used for send push messages to GCM
 * 
 * @author Bohdan_Kolesnyk
 *
 */
public class GCMContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> registration_ids;
    private Map<String, String> data;

    /** Add RegId to content */
    public void addRegId(String regId) {
        if (registration_ids == null)
            registration_ids = new LinkedList<String>();
        registration_ids.add(regId);
    }

    /** Create data with title and message for content */
    public void createData(String title, String message) {
        if (data == null)
            data = new HashMap<String, String>();

        data.put("title", title);
        data.put("message", message);
    }
}
