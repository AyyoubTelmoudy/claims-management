package com.gdr.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="complaints")
public class Complaint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1400811613864459323L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false,unique = true)
	private String publicId;
	@ManyToOne(cascade = CascadeType.ALL)
	private ClientProject clientProject;
	@Column(nullable = false)
	private long ordinalNumber=1;
	@ManyToOne(cascade = CascadeType.ALL)
	private ComplaintType complaintType;
	@Column(nullable = false)
	private Long ticketNumber;
	@Column(nullable = false)
	private LocalDate complaintDate=LocalDate.now();
	@Column(nullable = false)
	private String description;
	@Column(nullable = true)
	private LocalDate closingDate;
	@Column(nullable = false)
	private boolean consultedByCollaborator=false;
	@Column(nullable = false)
	private String status;
	
	public boolean isConsultedByCollaborator() {
		return consultedByCollaborator;
	}

	public void setConsultedByCollaborator(boolean consultedByCollaborator) {
		this.consultedByCollaborator = consultedByCollaborator;
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

	public ClientProject getClientProject() {
		return clientProject;
	}

	public void setClientProject(ClientProject clientProject) {
		this.clientProject = clientProject;
	}

	public ComplaintType getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(ComplaintType complaintType) {
		this.complaintType = complaintType;
	}

	public Long getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(Long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public LocalDate getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(LocalDate complaintDate) {
		this.complaintDate = complaintDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Complaint() {
		super();
	}

}
