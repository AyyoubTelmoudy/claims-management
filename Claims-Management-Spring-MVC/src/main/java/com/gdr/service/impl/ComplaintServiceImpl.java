package com.gdr.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gdr.entities.Attachment;
import com.gdr.entities.ClientProject;
import com.gdr.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.gdr.entities.Client;
import com.gdr.entities.Collaborator;
import com.gdr.entities.Complaint;
import com.gdr.entities.ComplaintType;
import com.gdr.entities.Message;
import com.gdr.repositories.ClientProjectRepository;
import com.gdr.repositories.ClientRepository;
import com.gdr.repositories.AttachmentRepository;
import com.gdr.repositories.ProjectRepository;
import com.gdr.repositories.ComplaintRepository;
import com.gdr.repositories.ComplaintTypeRepository;
import com.gdr.repositories.ConversationRepository;
import com.gdr.repositories.MessageRepository;
import com.gdr.repositories.UserRepository;
import com.gdr.services.ComplaintService;
import com.gdr.shared.Utils;
import com.gdr.dto.ComplaintDto;

@Service
public class ComplaintServiceImpl implements ComplaintService{
    
	@Autowired
	ComplaintRepository complaintRepository;
	@Autowired
	ComplaintTypeRepository typeReclamationRepository;
	@Autowired
	UserRepository utilisateurRepository;
	@Autowired
	ProjectRepository projetRepository;
	@Autowired
	ClientProjectRepository clientProjetRepository;
	@Autowired
	AttachmentRepository pieceJointRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ConversationRepository conversationRepository;
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	SharedService sharedService;
	
	
	@Override
	public void saveComplaint(ComplaintDto complaintDto, MultipartFile pieceJoint)
	{	 	
		User authenticatedUser=sharedService.getAuthenticatedUser();
		ComplaintType typeReclamation=typeReclamationRepository.findByName(complaintDto.getComplaintType());
		Complaint reclamation=new Complaint();
		List<ClientProject> listclientprojets=clientProjetRepository.findByClient(clientRepository.findByUser(authenticatedUser));
		for(ClientProject clientProject :listclientprojets)
		{
			if(clientProject.getProject().getName().equals(complaintDto.getProjectName()))
			{
		        reclamation.setClientProject(clientProject);
				break;
			}
		}
		reclamation.setComplaintType(typeReclamation);
		reclamation.setDescription(complaintDto.getDescription());
        reclamation.setTicketNumber(getNextTicketNum(clientRepository.findByUser(authenticatedUser)));
        reclamation.setStatus("Saisie");
        reclamation.setPublicId(Utils.genereteRandomString(30));
        reclamation.setOrdinalNumber(1); 
        List<ClientProject> clientProjects=clientProjetRepository.findByCollaborator(reclamation.getClientProject().getCollaborator());
		List<Complaint> clientComplaints=new ArrayList<Complaint>();
        for (ClientProject clientProject : clientProjects) {
        	clientComplaints.addAll(complaintRepository.findByClientProject(clientProject));
		}
        for (int i = 0; i <clientComplaints.size(); i++) {
        		clientComplaints.get(i).setOrdinalNumber(clientComplaints.get(i).getOrdinalNumber()+1);
        		complaintRepository.save(clientComplaints.get(i));
		}        
        Complaint savedReclamation=complaintRepository.save(reclamation);
        if((null != pieceJoint && pieceJoint.isEmpty()==false))
        {
    		String nomPiece=pieceJoint.getOriginalFilename().split("\\.")[0];
    		String pieceExtension=pieceJoint.getOriginalFilename().split("\\.")[1];
    		try {   
    			    String randomString=Utils.genereteRandomString(30);
    			    Attachment newPieceJoint=new Attachment();
    			    newPieceJoint.setComplaint(savedReclamation);
    			    newPieceJoint.setAttachmentName(nomPiece);
    			    newPieceJoint.setPath("/upload/" +randomString+"."+pieceExtension);
    			    newPieceJoint.setPublicId(Utils.genereteRandomString(30));
    			    pieceJointRepository.save(newPieceJoint);
    			    pieceJoint.transferTo(new File(FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"\\src\\main\\resources\\upload\\"+randomString+"."+pieceExtension));
    		    } 
    		catch (IllegalStateException e) {} 
    		catch (IOException e) {}
        }
        Client client=clientRepository.findByUser(authenticatedUser);
        client.setStartingEnteringNextComplaintDate(null);
        clientRepository.save(client);
	}
	@Override
	public List<ComplaintDto> clientComplaints(Client client) {	
		List<ClientProject> listclientprojets=clientProjetRepository.findByClient(client);
		List<ComplaintDto> complaintsDto=new ArrayList<ComplaintDto>();
        
        for(ClientProject clientProjet : listclientprojets)
        {
        	List<Complaint> complaints=new ArrayList<Complaint>();
        	complaints=complaintRepository.findByClientProject(clientProjet);
        	for(Complaint complaint : complaints)
        	{
            	ComplaintDto complaintDto=new ComplaintDto();
            	BeanUtils.copyProperties(complaint,complaintDto);
            	complaintDto.setClientSocialReason(complaint.getClientProject().getClient().getSocialReason());
            	complaintDto.setComplaintType(complaint.getComplaintType().getName());
            	complaintDto.setProjectName(complaint.getClientProject().getProject().getName());
            	complaintsDto.add(complaintDto);
        	}
        }   
		return complaintsDto;
	}

	@Override
	public long getNextTicketNum(Client client) {
		List<ClientProject> clientProjets=clientProjetRepository.findByClient(client);
		List<Complaint> listReclamations=new ArrayList<Complaint>();
			
		for(ClientProject clientProjet : clientProjets)
		{
			listReclamations.addAll(complaintRepository.findByClientProject(clientProjet));
		}
			if(listReclamations.size()==0)
			{
				return 1;
			}
			return listReclamations.get(listReclamations.size()-1).getTicketNumber()+1;
			
	}

	@Override
	public List<ComplaintDto> getAllComplaints() {
		List<Complaint> complaints=(List<Complaint>) complaintRepository.findAll();
		List<ComplaintDto> complaintsDto=new ArrayList<ComplaintDto>();
    	for(Complaint complaint : complaints)
    	{
        	ComplaintDto complaintDto=new ComplaintDto();
        	BeanUtils.copyProperties(complaint,complaintDto);
        	complaintDto.setClientSocialReason(complaint.getClientProject().getClient().getSocialReason());
        	complaintDto.setProjectName(complaint.getClientProject().getProject().getName());
        	complaintDto.setComplaintType(complaint.getComplaintType().getName());
        	complaintDto.setCollaboratorEmail(complaint.getClientProject().getCollaborator().getEmail());
        	complaintsDto.add(complaintDto);
    	}
		
		return complaintsDto;
	}

	@Override
	public ComplaintDto getComplaint(String publicId) {	
		ComplaintDto complaintDto=new ComplaintDto();
		Complaint complaint=complaintRepository.findByPublicId(publicId);
		if(complaint==null)
		{
			return null;
		}
		else
		{
	    	BeanUtils.copyProperties(complaint,complaintDto);
	    	complaintDto.setClientSocialReason(complaint.getClientProject().getClient().getSocialReason());
	    	complaintDto.setProjectName(complaint.getClientProject().getProject().getName());
	    	complaintDto.setComplaintType(complaint.getComplaintType().getName());
	    	complaintDto.setCollaboratorEmail(complaint.getClientProject().getCollaborator().getEmail());
			return complaintDto;
		}
	}

	
	@Override
	public ComplaintDto getComplaintByConversationPublicId(String publicId) {
		ComplaintDto complaintDto=new ComplaintDto();
		Complaint complaint=conversationRepository.findByPublicId(publicId).getComplaint();
    	BeanUtils.copyProperties(complaint,complaintDto);
    	complaintDto.setClientSocialReason(complaint.getClientProject().getClient().getSocialReason());
    	complaintDto.setProjectName(complaint.getClientProject().getProject().getName());
    	complaintDto.setComplaintType(complaint.getComplaintType().getName());
    	complaintDto.setCollaboratorEmail(complaint.getClientProject().getCollaborator().getEmail());
		return complaintDto;
	}
	
	
	@Override
	public List<ComplaintDto> getCollaboratorComplaints() {
		List<ComplaintDto> complaintsDto=new ArrayList<ComplaintDto>();
		Collaborator collaborator=sharedService.getAuthenticatedCollaborator();
		List<ClientProject> clientsprojects=clientProjetRepository.findByCollaborator(collaborator);
    	List<Complaint> complaints=new ArrayList<Complaint>();
        for(ClientProject clientProjet : clientsprojects)
        {
        	complaints.addAll(complaintRepository.findByClientProject(clientProjet));
        }  
		long size=complaints.size();
		List<Complaint> sortedComplaints=new ArrayList<Complaint>();
		for(int i = 0; i < size; i++)
		{
			for (int j = 0; j < complaints.size(); j++) 
			{
				if(complaints.get(j).getOrdinalNumber()==1+i)
				{
					sortedComplaints.add(complaints.remove(j));
				}	
			}
		}	
    	for(Complaint complaint : sortedComplaints)
    	{
        	ComplaintDto complaintDto=new ComplaintDto();
        	BeanUtils.copyProperties(complaint,complaintDto);
        	complaintDto.setClientSocialReason(complaint.getClientProject().getClient().getSocialReason());
        	complaintDto.setComplaintType(complaint.getComplaintType().getName());
        	complaintDto.setProjectName(complaint.getClientProject().getProject().getName());
        	complaintDto.setClientSocialReason(complaint.getClientProject().getClient().getSocialReason());
        	complaintDto.setConsultedByCollaborator(complaint.isConsultedByCollaborator());
        	if(conversationRepository.findByComplaint(complaint)!=null)
        	{
        		List<Message> messages=messageRepository.findByConversation(conversationRepository.findByComplaint(complaint));
        		if(messages.size()>0)
        		{
        			complaintDto.setDescription(messages.get(messages.size()-1).getMessageContent());
        			complaintDto.setPicture(messages.get(messages.size()-1).isPicture());
        			
        		}
        	}
        	complaintsDto.add(complaintDto);
    	}
		return complaintsDto;
	}


	
	@Override
	public ComplaintDto closeComplaint(String publicId) {
		Complaint complaint=complaintRepository.findByPublicId(publicId);
		if(complaint==null)
		{
			return null;
		}
		else
		{
			complaint.setClosingDate(LocalDate.now());
			complaint.setStatus("Traitée et Annulée");
			complaintRepository.save(complaint);
			ComplaintDto complaintDto=new ComplaintDto();
			BeanUtils.copyProperties(complaint,complaintDto);
			return complaintDto;
		}
	}

	@Override
	public void setConsultedByCollaborator(String publicId, boolean b) {
		Complaint complaint=complaintRepository.findByPublicId(publicId);
		complaint.setStatus("En cours de traitement");
		complaint.setConsultedByCollaborator(b);
	}



}
