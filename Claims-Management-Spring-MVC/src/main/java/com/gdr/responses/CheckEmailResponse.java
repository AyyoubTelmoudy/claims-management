package com.gdr.responses;

import java.io.Serializable;

public class CheckEmailResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3556206398710171181L;
	private String message;
	
	public CheckEmailResponse() {
		super();
	}
	public CheckEmailResponse(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
