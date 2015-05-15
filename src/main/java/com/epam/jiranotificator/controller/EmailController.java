package com.epam.jiranotificator.controller;

import com.epam.jiranotificator.service.impl.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailSender emailSender;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Email page";
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void push(@RequestParam String subject, @RequestParam String msg){
        emailSender.send(subject, msg);
    }
}
