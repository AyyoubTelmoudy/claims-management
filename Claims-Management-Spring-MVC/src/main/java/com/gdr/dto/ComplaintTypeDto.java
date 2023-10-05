package com.gdr.dto;

import java.io.Serializable;

public class ComplaintTypeDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3556206398710171181L;
	private String publicId;
	private String name;
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


}
