package com.gdr.dto;

import java.io.Serializable;

public class ClientDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9191623770251346626L;
	
	private String socialReason;
	private String email;
	private String phoneNumber;
	private String publicId;
	private String projectName;
	private String collaboratorEmail;
	private String status;
	
	public String getSocialReason() {
		return socialReason;
	}
	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCollaboratorEmail() {
		return collaboratorEmail;
	}
	public void setCollaboratorEmail(String collaboratorEmail) {
		this.collaboratorEmail = collaboratorEmail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
