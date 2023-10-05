package com.gdr.dto;

import java.io.Serializable;

public class ProjectDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String publicId;
	private String name;
	private String status;
	private String clientSocialReason;
	private String collaboratorEmail;
	public String getCollaboratorEmail() {
		return collaboratorEmail;
	}
	public void setCollaboratorEmail(String collaboratorEmail) {
		this.collaboratorEmail = collaboratorEmail;
	}
	public String getClientSocialReason() {
		return clientSocialReason;
	}
	public void setClientSocialReason(String clientSocialReason) {
		this.clientSocialReason = clientSocialReason;
	}
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}
