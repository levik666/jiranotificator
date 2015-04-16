package com.epam.jiranotificator.utils;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.epam.jiranotificator.dto.IssueDTO;

public class JsonParser {
	
	private IssueDTO issueDTO;
	private JSONArray issues;
	private JSONObject fields;
	
	public IssueDTO readJson (JSONObject jsonFromJira) {
		issueDTO = new IssueDTO();
		
		if (!jsonFromJira.isEmpty()) {
			issues = (JSONArray) jsonFromJira.get("issues");
			
			Iterator<JSONObject> iterator = issues.iterator();
			while (iterator.hasNext()) {
				
			JSONObject issue = (JSONObject) iterator.next(); 
						
			fields = (JSONObject) issue.get("fields");
			JSONObject priority = (JSONObject) fields.get("priority");

			issueDTO.setCode((String) issue.get("key"));
			issueDTO.setStatus((String) priority.get("name"));
			issueDTO.setDescription((String) fields.get("description"));
			
			return issueDTO;
			}
		}
		return null;
	}
}
