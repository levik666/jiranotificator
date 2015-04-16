package com.epam.jiranotificator.dto;


/*
 * DTO from Jira to GCM
 * */

public class IssueDTO {

	private String code;
	private String status;
	private String description;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        final IssueDTO issueDTO = (IssueDTO) obj;
        return (this.code == issueDTO.code) &&
               (this.status == issueDTO.status) &&
               (this.description == issueDTO.description);

    }

    @Override
    public int hashCode() {
    	int result = 0;
    	result = 31*result + (code !=null ? code.hashCode() : 0);
    	result = 31*result + (status !=null ? status.hashCode() : 0);
    	result = 31*result + (description !=null ? description.hashCode() : 0);
        return result;
    }
}
