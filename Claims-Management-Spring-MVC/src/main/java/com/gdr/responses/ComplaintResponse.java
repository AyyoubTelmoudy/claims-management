package com.gdr.responses;

import java.io.Serializable;

public class ComplaintResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9191623770251346626L;
	
	private String conversationPublicId;
	private long ticketNumber;
	private String lastMessageContent;
	private boolean isPicture;
	private boolean consultedByClient;
	public boolean isPicture() {
		return isPicture;
	}
	public void setPicture(boolean isPicture) {
		this.isPicture = isPicture;
	}
	public String getConversationPublicId() {
		return conversationPublicId;
	}
	public void setConversationPublicId(String conversationPublicId) {
		this.conversationPublicId = conversationPublicId;
	}
	public long getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getLastMessageContent() {
		return lastMessageContent;
	}
	public void setLastMessageContent(String lastMessageContent) {
		this.lastMessageContent = lastMessageContent;
	}
	public boolean isConsultedByClient() {
		return consultedByClient;
	}
	public void setConsultedByClient(boolean consultedByClient) {
		this.consultedByClient = consultedByClient;
	}
	

}
