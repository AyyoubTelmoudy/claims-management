package com.gdr.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="admins")
public class Admin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1400811613864459323L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false,length = 120,unique = true)
	private String login;
	@Column(nullable = false)
	private String encryptedPassword;
	@Column(nullable = false,length = 120)
	private String email;
	
	public Admin() {
		super();
	}
	public Admin(String login, String encryptedPassword, String email) {
		super();
		this.login = login;
		this.encryptedPassword = encryptedPassword;
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
