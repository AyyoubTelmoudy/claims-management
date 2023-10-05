package com.gdr.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1400811613864459323L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false,unique = true)
	private String publicId;
	@Column(nullable = false,length = 120,unique = true)
	private String login;
	@Column(nullable = false)
	private String encryptedPassword;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
    private String role ; 
	
    public User() {
		super();
	}


	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
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


	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public boolean hasRole(String role) {
            if (this.role.equals(role)) 
                return true;
             else
                return false;
    }
    
	
}
