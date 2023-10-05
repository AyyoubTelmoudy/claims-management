package com.gdr.dto;

import java.io.Serializable;

public class AttachmentDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3556206398710171181L;
	private String publicId;
	private String attachmentName;
	private String path;
	
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

}
