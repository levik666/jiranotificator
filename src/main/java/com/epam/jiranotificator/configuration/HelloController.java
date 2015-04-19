package com.epam.jiranotificator.configuration;


import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.jiranotificator.service.JiraService;

/**
 * Simple controller
 * @author Bohdan_Kolesnyk
 *
 */
@RestController
@Configuration
@PropertySource("classpath:jira.properties")
public class HelloController {
    
    @Value("${query}")
    private String query;
    
    @Autowired
    JiraService jiraUtil;

    @RequestMapping("/")
    public String index() throws URISyntaxException, IOException {
        System.out.println(jiraUtil.getIssuesByQuery(query).size());
        return "Greetings from Spring Boot!";
    }

}
