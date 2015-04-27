package com.epam.jiranotificator.configuration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Controller which starts notification every 20 sec.
 * 
 * @author Bohdan_Kolesnyk
 *
 */
@Component
public class NotificationController {

    /**
     * Start notification process
     */
    @Scheduled(cron = "${crone.expression}")
    public void notificate() {
        // put scheduled code here
        System.out.println("Notificate");// Should be removed
    }
}
