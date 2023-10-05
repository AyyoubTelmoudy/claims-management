package com.gdr.dto;

import java.io.Serializable;

public class ProjectAssignmentDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectName;
	private String clientEmail;
	private String collaboratorEmail;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public String getCollaboratorEmail() {
		return collaboratorEmail;
	}
	public void setCollaboratorEmail(String collaboratorEmail) {
		this.collaboratorEmail = collaboratorEmail;
	}


}
