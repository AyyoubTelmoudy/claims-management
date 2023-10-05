package com.gdr.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity(name="clients")
public class Client implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1400811613864459323L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false,unique = true)
	private String publicId;
	@Column(nullable = false,unique = true)
	private String socialReason;
	@Column(nullable = false,length = 120,unique = true)
	private String email;
	@Column(nullable = false,length =30)
	private String phoneNumber;
	@Column(nullable =true)
	private Date startingEnteringNextComplaintDate;
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
	@Column(nullable = false)
	private String status;
	public Client() {
		super();
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public Date getStartingEnteringNextComplaintDate() {
		return startingEnteringNextComplaintDate;
	}
	public void setStartingEnteringNextComplaintDate(Date startingEnteringNextComplaintDate) {
		this.startingEnteringNextComplaintDate = startingEnteringNextComplaintDate;
	}
	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public String getSocialReason() {
		return socialReason;
	}

	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
