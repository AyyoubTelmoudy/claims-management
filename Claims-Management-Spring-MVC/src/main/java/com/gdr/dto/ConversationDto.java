package com.gdr.dto;

import java.io.Serializable;



public class ConversationDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3556206398710171181L;
	private String publicId;
	private boolean consultedByClient;
	private boolean consultedByCollaborator;
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public boolean isConsultedByClient() {
		return consultedByClient;
	}
	public void setConsultedByClient(boolean consultedByClient) {
		this.consultedByClient = consultedByClient;
	}
	public boolean isConsultedByCollaborator() {
		return consultedByCollaborator;
	}
	public void setConsultedByCollaborator(boolean consultedByCollaborator) {
		this.consultedByCollaborator = consultedByCollaborator;
	}
	


}
