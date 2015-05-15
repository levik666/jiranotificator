package com.epam.jiranotificator.controller;

import com.atlassian.jira.rest.client.api.domain.BasicPriority;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.epam.jiranotificator.dto.IssueDTO;
import com.epam.jiranotificator.service.impl.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/jira")
public class JiraController {

    @Autowired
    private JiraService jiraService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Set<IssueDTO> index() {
        return convert(jiraService.getIssues());
    }

    private Set<IssueDTO> convert(final Set<Issue> issues){
        Set<IssueDTO> issueDTOs = new HashSet<>();
        for(final Issue issue : issues){
            final IssueDTO issueDTO = new IssueDTO();
            issueDTO.setTitle(issue.getSummary());
            issueDTO.setJiraNumber(issue.getKey());
            final BasicPriority priority = issue.getPriority();
            if (priority != null){
                issueDTO.setPriority(priority.getName());
            }
            issueDTOs.add(issueDTO);
        }

        return issueDTOs;
    }
}
