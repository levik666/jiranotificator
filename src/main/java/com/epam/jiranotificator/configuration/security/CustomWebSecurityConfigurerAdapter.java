package com.epam.jiranotificator.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private static final String USER = "USER";

    @Value("${jira.alert.emailLogin}")
    private String login;
    @Value("${jira.alert.emailPassword}")
    private String password;

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) {
        try {
            auth.inMemoryAuthentication()
                    .withUser(login)
                    .password(password)
                    .roles(USER);
        } catch (Exception exe) {
            throw new RuntimeException("Can't customize security configuration " + exe.getMessage(), exe);
        }
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/").permitAll()
                .anyRequest().fullyAuthenticated();
        http.httpBasic();
    }
}
