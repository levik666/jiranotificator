package com.epam.jiranotificator.controller;

import com.epam.jiranotificator.service.GCMSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mobile")
public class PushController {

    @Autowired
    private GCMSender gcmNotificationSender;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Mobile page";
    }


    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public void push(@RequestParam String message){
        gcmNotificationSender.send(message);
    }
}
