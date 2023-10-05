package com.gdr.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ComplaintDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9191623770251346626L;
	
	private String description;
	private String publicId;
	private String complaintType;
	private String projectName;
	private String status;
    private LocalDate complaintDate;
    private LocalDate  closingDate;
	private long ticketNumber;
	private String clientSocialReason;
	private String collaboratorEmail;
	private boolean consultedByCollaborator;
	private boolean isPicture=false;
	public boolean isPicture() {
		return isPicture;
	}
	public void setPicture(boolean isPicture) {
		this.isPicture = isPicture;
	}
	public boolean isConsultedByCollaborator() {
		return consultedByCollaborator;
	}
	public void setConsultedByCollaborator(boolean consultedByCollaborator) {
		this.consultedByCollaborator = consultedByCollaborator;
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public String  getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getComplaintDate() {
		return complaintDate;
	}
	public void setComplaintDate(LocalDate complaintDate) {
		this.complaintDate = complaintDate;
	}
	public LocalDate getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}
	public long getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	

	
}
