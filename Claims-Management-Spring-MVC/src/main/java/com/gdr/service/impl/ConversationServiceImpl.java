package com.gdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gdr.entities.ClientProject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdr.entities.Complaint;
import com.gdr.entities.Conversation;
import com.gdr.entities.Message;
import com.gdr.repositories.ClientProjectRepository;
import com.gdr.repositories.ComplaintRepository;
import com.gdr.repositories.ConversationRepository;
import com.gdr.repositories.MessageRepository;
import com.gdr.responses.ComplaintResponse;
import com.gdr.services.ConversationService;
import com.gdr.dto.ConversationDto;

@Service
public class ConversationServiceImpl implements ConversationService{
     
	@Autowired
	ConversationRepository conversationRepository;
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	ComplaintRepository complaintRepository;
	@Autowired
	ClientProjectRepository clientProjectRepository;
	@Autowired
	SharedService sharedService;
	
	
	@Override
	public List<ComplaintResponse> getResponses() {
		List<ClientProject> clientProjects=clientProjectRepository.findByClient(sharedService.getAuthenticatedClient());
		List<Complaint> complaints=new ArrayList<Complaint>();
		for (ClientProject clientProject : clientProjects) 
		{
			complaints.addAll(complaintRepository.findByClientProject(clientProject));
		}

		List<Conversation> conversations=new ArrayList<Conversation>();
		for (Complaint complaint : complaints) 
		{
			if(conversationRepository.findByComplaint(complaint)!=null)
			{
				conversations.add(conversationRepository.findByComplaint(complaint));
			}
		}
		
		long size=conversations.size();
		List<Conversation> sortedConversations=new ArrayList<Conversation>();
		for(int i = 0; i < size; i++)
		{	
			for (int j = 0; j < conversations.size(); j++) 
			{
				if(conversations.get(j).getOrdinalNumber()==i+1)
				{
					sortedConversations.add(conversations.remove(j));
				}
			}
		}	
		List<ComplaintResponse> responses=new ArrayList<ComplaintResponse>();
		for (Conversation conversation : sortedConversations) 
		{	
			List<Message> messages=messageRepository.findByConversation(conversation);
			if(messages.size()>0)
			{
				ComplaintResponse complaintResponse=new ComplaintResponse();
				complaintResponse.setLastMessageContent(messages.get(messages.size()-1).getMessageContent());
				complaintResponse.setConversationPublicId(conversation.getPublicId());
				complaintResponse.setConsultedByClient(conversation.isConsultedByClient());
				complaintResponse.setTicketNumber(conversation.getComplaint().getTicketNumber());
				complaintResponse.setPicture(messages.get(messages.size()-1).isPicture());
				responses.add(complaintResponse);
			}
			
		}	
		return responses;
	}

	
	
	
	@Override
	public ConversationDto getConversation(String publicId) {
		Conversation conversation=conversationRepository.findByPublicId(publicId);
		if(conversation==null)
		{
			return null;
		}
		else
		{
			ConversationDto conversationDto=new ConversationDto();
			BeanUtils.copyProperties(conversation,conversationDto);
			conversation.setConsultedByClient(true);
			conversationRepository.save(conversation);
			return conversationDto;
		}

	}
	@Override
	public ConversationDto getConversationByComplaintPublicId(String publicId) {
		
		Conversation conversation=conversationRepository.findByComplaint(complaintRepository.findByPublicId(publicId));
		if (conversation==null)
		{
			return null;
		}
		ConversationDto conversationDto=new ConversationDto();
		BeanUtils.copyProperties(conversation,conversationDto);
		conversationRepository.save(conversation);
		return conversationDto;
	}
	@Override
	public void setConsultedByClient(String publicId, boolean b) {
		Conversation conversation=conversationRepository.findByPublicId(publicId);
		conversation.setConsultedByClient(b);
		conversationRepository.save(conversation);	
	}
	@Override
	public void setConsultedByCollaborator(String publicId, boolean b) {
		Conversation conversation=conversationRepository.findByComplaint(complaintRepository.findByPublicId(publicId));
		conversation.setConsultedByCollaborator(b);
		conversationRepository.save(conversation);
		
	}

}
