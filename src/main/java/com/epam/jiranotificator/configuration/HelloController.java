package com.epam.jiranotificator.configuration;


import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple controller
 * @author Bohdan_Kolesnyk
 *
 */
@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() throws URISyntaxException, IOException {
        return "Greetings from Spring Boot!";
    }

}
