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
@Entity(name="clientsProjects")
public class ClientProject implements Serializable{

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
	private Project project;
	@ManyToOne(cascade = CascadeType.ALL)
	private Client client;
	@ManyToOne(cascade = CascadeType.ALL)
	private Collaborator collaborator;
	@Column(nullable = false)
	private LocalDate assignmentDate=LocalDate.now();

	public ClientProject() {
		super();
	}
	public Collaborator getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(Collaborator collaborator) {
		this.collaborator = collaborator;
	}

	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public LocalDate getAssignmentDate() {
		return assignmentDate;
	}
	public void setAssignmentDate(LocalDate assignmentDate) {
		this.assignmentDate = assignmentDate;
	}


	
}
