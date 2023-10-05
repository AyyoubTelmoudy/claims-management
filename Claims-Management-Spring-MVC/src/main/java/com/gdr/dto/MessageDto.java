package com.gdr.dto;

import java.io.Serializable;
import java.util.Date;


public class MessageDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3556206398710171181L;
	private String publicId;
	private Date messageDate;
	private String messageContent;	
	private String sender;
	private boolean isPicture=false;	
	public boolean isPicture() {
		return isPicture;
	}
	public void setPicture(boolean isPicture) {
		this.isPicture = isPicture;
	}
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public Date getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}	


}
