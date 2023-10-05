package com.gdr.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity(name="conversations")
public class Conversation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1400811613864459323L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false,unique = true)
	private String publicId;
	@OneToOne(cascade = CascadeType.ALL)
	private Complaint complaint;
	@Column(nullable = false)
	private long ordinalNumber=1;
	@Column(nullable = false)
	private boolean consultedByClient=false;
	@Column(nullable = false)
	private boolean consultedByCollaborator=false;
	public Conversation() {
		super();
	}
	
	public long getOrdinalNumber() {
		return ordinalNumber;
	}

	public void setOrdinalNumber(long ordinalNumber) {
		this.ordinalNumber = ordinalNumber;
	}

	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public Complaint getComplaint() {
		return complaint;
	}
	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
	public boolean isConsultedByClient() {
		return consultedByClient;
	}
	public void setConsultedByClient(boolean consultedByClient) {
		this.consultedByClient = consultedByClient;
	}
	public boolean isConsultedByCollaborator() {
		return consultedByCollaborator;
	}
	public void setConsultedByCollaborator(boolean consultedByCollaborator) {
		this.consultedByCollaborator = consultedByCollaborator;
	}

	
}