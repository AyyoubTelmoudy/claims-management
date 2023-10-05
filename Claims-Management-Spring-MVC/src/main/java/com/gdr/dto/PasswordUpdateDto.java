package com.gdr.dto;

import java.io.Serializable;


public class PasswordUpdateDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3556206398710171181L;
	private String currentPassword;
	private String newPassword;
	private String newPasswordConfirmation;
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPasswordConfirmation() {
		return newPasswordConfirmation;
	}
	public void setNewPasswordConfirmation(String newPasswordConfirmation) {
		this.newPasswordConfirmation = newPasswordConfirmation;
	}
	

}
