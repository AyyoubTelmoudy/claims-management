package com.gdr.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gdr.entities.ClientProject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gdr.entities.Complaint;
import com.gdr.entities.Conversation;
import com.gdr.entities.Message;
import com.gdr.repositories.ClientProjectRepository;
import com.gdr.repositories.ComplaintRepository;
import com.gdr.repositories.ConversationRepository;
import com.gdr.repositories.MessageRepository;
import com.gdr.services.MessageService;
import com.gdr.shared.Utils;
import com.gdr.dto.ComplaintDto;
import com.gdr.dto.ConversationDto;
import com.gdr.dto.MessageDto;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	ConversationRepository conversationRepository;
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	ComplaintRepository complaintRepository;
	@Autowired
	ClientProjectRepository clientProjectRepository;
	
	
	@Override
	public List<MessageDto> getConversationMessages(String publicId) {
		Conversation conversation=conversationRepository.findByPublicId(publicId);
		List<MessageDto> messagesDto=new ArrayList<MessageDto>();
		List<Message> messages=messageRepository.findByConversation(conversation);
		
		for (Message message : messages) {
			MessageDto messageDto=new MessageDto();
			BeanUtils.copyProperties(message,messageDto);
			messagesDto.add(messageDto);
		}
		
		return messagesDto;
	}

	@Override
	public List<MessageDto> getComplaintConversationMessages(String publicId) {
		
		Conversation conversation=conversationRepository.findByComplaint(complaintRepository.findByPublicId(publicId));
		List<MessageDto> messagesDto=new ArrayList<MessageDto>();
		List<Message> messages=messageRepository.findByConversation(conversation);
		
		for (Message message : messages) {
			MessageDto messageDto=new MessageDto();
			BeanUtils.copyProperties(message,messageDto);
			messagesDto.add(messageDto);
		}
		
		return messagesDto;
	}
	
	
	@Override
	public ComplaintDto saveCollaboratorMessage(MessageDto newMessageDto, String complaintPublicId) {
		Conversation converastion=conversationRepository.findByComplaint(complaintRepository.findByPublicId(complaintPublicId));
		if(complaintRepository.findByPublicId(complaintPublicId)==null)
		{
			return null;
		}
		else
		{
			if(converastion==null)
			{
				converastion=new Conversation();
				converastion.setComplaint(complaintRepository.findByPublicId(complaintPublicId));
				converastion.setConsultedByClient(false);
				converastion.setConsultedByCollaborator(true);
				converastion.setOrdinalNumber(1);
				converastion.setPublicId(Utils.genereteRandomString(30));

				List<ClientProject> clientProjects=clientProjectRepository.findByClient(complaintRepository.findByPublicId(complaintPublicId).getClientProject().getClient());
				List<Complaint> clientComplaints=new ArrayList<Complaint>();
				for (ClientProject clientProject : clientProjects) {	
					clientComplaints.addAll(complaintRepository.findByClientProject(clientProject));
				}
				List<Conversation> clientConversations=new ArrayList<Conversation>();
				for (Complaint complaint : clientComplaints) {
					if(conversationRepository.findByComplaint(complaint)!=null)
					{
						clientConversations.add(conversationRepository.findByComplaint(complaint));
					}
				}
				
		        for (int i = 0; i <clientConversations.size(); i++) { 	
		        	Conversation conversation=clientConversations.get(i);
		        		conversation.setOrdinalNumber(conversation.getOrdinalNumber()+1);
		        		conversationRepository.save(conversation);
				} 
		        
				Conversation savedConversation=conversationRepository.save(converastion);
				Message message=new Message();
				message.setPublicId(Utils.genereteRandomString(30));
				message.setMessageContent(newMessageDto.getMessageContent());
				message.setConversation(savedConversation);
				message.setMessageDate(new Date());
				message.setSender("COLLABORATOR");
				messageRepository.save(message);
			}
			else
			{	
			converastion.setConsultedByCollaborator(true);
			converastion.setConsultedByClient(false);
			long ordinalNumber=converastion.getOrdinalNumber();
			Conversation savedConversation=conversationRepository.save(converastion);
			List<ClientProject> clientProjects=clientProjectRepository.findByClient(complaintRepository.findByPublicId(complaintPublicId).getClientProject().getClient());
			List<Complaint> clientComplaints=new ArrayList<Complaint>();
			for (ClientProject clientProject : clientProjects) {	
				clientComplaints.addAll(complaintRepository.findByClientProject(clientProject));
			}
	        for (int i = 0; i <clientComplaints.size(); i++) {
	        	Conversation conversation=conversationRepository.findByComplaint(clientComplaints.get(i));
	        	if (conversation!=null) 
	        	{	
	            	if(conversation.getOrdinalNumber()<ordinalNumber&&!conversation.getPublicId().equals(savedConversation.getPublicId()))
	            	{
	            		conversation.setOrdinalNumber(conversation.getOrdinalNumber()+1);
	            	}
	            	if(conversation.getPublicId().equals(savedConversation.getPublicId()))
	            	{
	            		conversation.setOrdinalNumber(1);
	            		conversation.setConsultedByClient(false);
	            	}		
				}
	        	conversationRepository.save(conversation);
			}       
			Message message=new Message();
			message.setPublicId(Utils.genereteRandomString(30));
			message.setMessageContent(newMessageDto.getMessageContent());
			message.setMessageDate(new Date());
			message.setSender("COLLABORATOR");
			message.setConversation(savedConversation);
			messageRepository.save(message);
			}	
			ComplaintDto complaintDto=new ComplaintDto();
			BeanUtils.copyProperties(complaintRepository.findByPublicId(complaintPublicId),complaintDto);
			return complaintDto;
		}
	}
	
	@Override
	public ComplaintDto saveCollaboratorMessageImage(MultipartFile image, String complaintPublicId) {
		if(complaintRepository.findByPublicId(complaintPublicId)==null)
		{
			return null;
		}
		else
		{
			String imageExtension=image.getOriginalFilename().split("\\.")[1];
			String randomString=Utils.genereteRandomString(30);
			Message newMessage=new Message();	
			Conversation conversation=conversationRepository.findByComplaint(complaintRepository.findByPublicId(complaintPublicId));
			if(conversation==null)
			{
				conversation= new Conversation();
				conversation.setPublicId(Utils.genereteRandomString(30));
				conversation.setComplaint(complaintRepository.findByPublicId(complaintPublicId));
				conversation.setConsultedByCollaborator(true);
				conversation.setConsultedByClient(false);
				conversation.setOrdinalNumber(1);
				Conversation savedConversation=conversationRepository.save(conversation);
				List<Complaint> clientComplaints=complaintRepository.findByClientProject(complaintRepository.findByPublicId(complaintPublicId).getClientProject());
		        for (int i = 0; i <clientComplaints.size(); i++) { 	
		        	Conversation convers=conversationRepository.findByComplaint(clientComplaints.get(i));
		        		convers.setOrdinalNumber(convers.getOrdinalNumber()+1);
		        		conversationRepository.save(convers);
				}  
	        	System.out.println("Salam 2");
				newMessage.setConversation(savedConversation);
			}
			else
			{

				newMessage.setConversation(conversation);
				conversation.setConsultedByCollaborator(true);
				conversation.setConsultedByClient(false);
				conversation.setOrdinalNumber(1);	
				long ordinalNumber=conversation.getOrdinalNumber();
				conversationRepository.save(conversation);
				List<Complaint> clientComplaints=complaintRepository.findByClientProject(conversation.getComplaint().getClientProject());
		        for (int i = 0; i <clientComplaints.size(); i++) {
		        	
		        	Conversation con=conversationRepository.findByComplaint(clientComplaints.get(i));
		        	
		        	if(!con.getPublicId().equals(conversation.getPublicId())&&con.getOrdinalNumber()<ordinalNumber)
		        	{
		        		con.setOrdinalNumber(con.getOrdinalNumber()+1);
		        		conversationRepository.save(con);
		        	}
				}  
			}
			newMessage.setMessageDate(new Date());
			newMessage.setPicture(true);
			newMessage.setSender("COLLABORATOR");
			newMessage.setPublicId(Utils.genereteRandomString(30));
	        newMessage.setMessageContent("/upload/" +randomString+"."+imageExtension);
	        messageRepository.save(newMessage);
	        
	        try {
				image.transferTo(new File(FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"\\src\\main\\resources\\upload\\"+randomString+"."+imageExtension));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        ComplaintDto complaintDto=new ComplaintDto();
	        BeanUtils.copyProperties(complaintRepository.findByPublicId(complaintPublicId),complaintDto); 
	        return complaintDto;
		}

	}
	
	@Override
	public ConversationDto saveClientMessage(MessageDto messageDto, String conversationPublicId) {
		Conversation converastion=conversationRepository.findByPublicId(conversationPublicId);
		if(converastion==null)
		{
			return null;
		}
		else
		{
			converastion.setConsultedByCollaborator(false);
			converastion.setConsultedByClient(true);	
			conversationRepository.save(converastion);
			Complaint complaint=converastion.getComplaint();
	        long ordinalNumber=complaint.getOrdinalNumber();
			complaint.setOrdinalNumber(1);
			complaint.setConsultedByCollaborator(false);
			complaintRepository.save(complaint);

			List<Complaint> collaboratorComplaints=new ArrayList<Complaint>();
	        List<ClientProject> clientProjects=clientProjectRepository.findByCollaborator(complaint.getClientProject().getCollaborator());
	        for (ClientProject clientProject : clientProjects) {
	        	collaboratorComplaints.addAll(complaintRepository.findByClientProject(clientProject));
			}
			
	        
	        for (int i = 0; i <collaboratorComplaints.size(); i++) {
	        	if(!collaboratorComplaints.get(i).getPublicId().equals(complaint.getPublicId())&&collaboratorComplaints.get(i).getOrdinalNumber()<ordinalNumber)
	        	{
	        		collaboratorComplaints.get(i).setOrdinalNumber(collaboratorComplaints.get(i).getOrdinalNumber()+1);
	        		complaintRepository.save(collaboratorComplaints.get(i));
	        	}
			}    
			Message message=new Message();
			message.setPublicId(Utils.genereteRandomString(30));
			message.setMessageContent(messageDto.getMessageContent());
			message.setConversation(converastion);
			message.setMessageDate(new Date());
			message.setSender("CLIENT");
			message.setConversation(converastion);
			messageRepository.save(message);
			ConversationDto conversationDto=new ConversationDto();
			BeanUtils.copyProperties(converastion,conversationDto);
			return conversationDto;
		}

		
		
	}
	
	
	@Override
	public ConversationDto saveClientMessageImage(MultipartFile image, String conversationPublicId) {
		Complaint complaint=conversationRepository.findByPublicId(conversationPublicId).getComplaint();		
		if(complaint==null)
		{
			return null;
		}
		else
		{
		String imageExtension=image.getOriginalFilename().split("\\.")[1];
		String randomString=Utils.genereteRandomString(30);
        long ordinalNumber=complaint.getOrdinalNumber();
		complaint.setOrdinalNumber(1);
		complaintRepository.save(complaint);
		List<Complaint> collaboratorComplaints=new ArrayList<Complaint>();
        List<ClientProject> clientProjects=clientProjectRepository.findByCollaborator(complaint.getClientProject().getCollaborator());
        for (ClientProject clientProject : clientProjects) {
        	collaboratorComplaints.addAll(complaintRepository.findByClientProject(clientProject));
		}
        
        for (int i = 0; i <collaboratorComplaints.size(); i++) {
        	if(!collaboratorComplaints.get(i).getPublicId().equals(complaint.getPublicId())&&collaboratorComplaints.get(i).getOrdinalNumber()<ordinalNumber)
        	{
        		collaboratorComplaints.get(i).setOrdinalNumber(collaboratorComplaints.get(i).getOrdinalNumber()+1);
        		complaintRepository.save(collaboratorComplaints.get(i));
        	}
		}       
		Message newMessage=new Message();
		newMessage.setConversation(conversationRepository.findByPublicId(conversationPublicId));
		newMessage.setMessageDate(new Date());
		newMessage.setPicture(true);
		newMessage.setSender("CLIENT");
		newMessage.setPublicId(Utils.genereteRandomString(30));
        newMessage.setMessageContent("/upload/" +randomString+"."+imageExtension);
        messageRepository.save(newMessage);
        try {
			image.transferTo(new File(FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"\\src\\main\\resources\\upload\\"+randomString+"."+imageExtension));

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
           ConversationDto conversationDto=new ConversationDto();
           BeanUtils.copyProperties(conversationRepository.findByPublicId(conversationPublicId), conversationDto);
          return conversationDto;
        
		}
	}
}
