package com.epam.jiranotificator.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.jiranotificator.service.JiraService;

/**
 * Simple controller
 * @author Bohdan_Kolesnyk
 *
 */
@RestController
public class HelloController {
    
    @Autowired
    JiraService jiraService;

    @RequestMapping("/")
    public String index() {
        jiraService.getJsonFromRequest();
        return "Greetings from Spring Boot!";
    }

}
