package com.gdr.controllers;

import java.util.List;

import com.gdr.dto.AttachmentDto;
import com.gdr.dto.ComplaintDto;
import com.gdr.dto.ConversationDto;
import com.gdr.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdr.responses.ComplaintResponse;
import com.gdr.services.AttachmentService;
import com.gdr.services.ComplaintService;
import com.gdr.services.ConversationService;
import com.gdr.services.MessageService;

@Controller
@RequestMapping
public class CommunicationController {

	
	@Autowired
	ConversationService conversationService;
	@Autowired
	MessageService messageService;
	@Autowired
	ComplaintService complaintService;
	@Autowired
	AttachmentService attachmentService;
	
	@GetMapping("/clients/complaints/responses")
	public String complaintsResponses(Model model) 
	{	
		List<ComplaintResponse> responses=conversationService.getResponses();
		model.addAttribute("responses",responses);
		return "complaintsResponses";	
	}

	
	
	@GetMapping("/collaborators/complaints/{publicId}/conversations")
	public String complaintsConversation(@PathVariable String publicId,Model model) 
	{
		ComplaintDto complaintDto=complaintService.getComplaint(publicId);
		if(complaintDto==null)
		{
			return "collaboratorNotFoundResource";
		}
		else
		{
			complaintService.setConsultedByCollaborator(publicId,true);
			model.addAttribute("complaintDto",complaintDto);
			AttachmentDto attachmentDto=attachmentService.getComplaintAttachmentByComplaintPublicId(publicId);
			model.addAttribute("attachmentDto",attachmentDto);	
			ConversationDto conversationDto=conversationService.getConversationByComplaintPublicId(publicId);
			if(conversationDto!=null)
			{
				conversationService.setConsultedByCollaborator(publicId,true);
				List<MessageDto> messagesDto=messageService.getComplaintConversationMessages(publicId);
				model.addAttribute("messagesDto",messagesDto);
			}
			model.addAttribute("newMessageDto",new MessageDto());
			return "collaboratorConversation";
		}
	
	}
	
	
	
	
	
	@GetMapping("/clients/complaints/convesations/{publicId}")
	public String getComplaintsResponses(@PathVariable String publicId,Model model) 
	{   	
		ConversationDto conversationDto=conversationService.getConversation(publicId);
		if(conversationDto==null)
		{
			return "clientNotFoundResource";
		}
		else
		{
			conversationService.setConsultedByClient(publicId,true);
			List<MessageDto> messagesDto=messageService.getConversationMessages(publicId);
			AttachmentDto attachmentDto=attachmentService.getComplaintAttachmentByConversationPublicId(publicId);
			ComplaintDto complaintDto=complaintService.getComplaintByConversationPublicId(publicId);
			model.addAttribute("newMessageDto",new MessageDto());
			model.addAttribute("complaintDto",complaintDto);
			model.addAttribute("attachmentDto",attachmentDto);
			model.addAttribute("conversationDto",conversationDto);
			model.addAttribute("messagesDto",messagesDto);	
			return "complaintConvesation";	
		}
	
	}

	@PostMapping("/clients/complaints/conversations/{publicId}/messages")
	public String saveClientMessage(@ModelAttribute("newMessageDto") MessageDto newMessageDto,@PathVariable("publicId") String conversationPublicId) 
	{   	
		if(messageService.saveClientMessage(newMessageDto,conversationPublicId)==null)
		{
			return "clientNotFoundResource";
		}
		else
		{
			return "redirect:/clients/complaints/convesations/"+conversationPublicId;
		}
	}
	
	@PostMapping("/clients/complaints/conversations/{publicId}/messages/image")
	public String saveClientMessageImage(@RequestParam("image") MultipartFile image,@PathVariable("publicId") String conversationPublicId) 
	{  
		
		if(messageService.saveClientMessageImage(image,conversationPublicId)==null)
		{
			return "clientNotFoundResource";
		}
		else
		{
			return "redirect:/clients/complaints/convesations/"+conversationPublicId;
		}
			
	}
	
	@PostMapping("/collaborators/complaints/{publicId}/conversations/messages")
	public String saveCollaboratorMessage(@ModelAttribute("newMessageDto") MessageDto newMessageDto,@PathVariable("publicId") String complaintPublicId) throws InterruptedException 
	{   
		if(messageService.saveCollaboratorMessage(newMessageDto,complaintPublicId)==null)
		{
			return "collaboratorNotFoundResource";
		}
		else
		{
			return "redirect:/collaborators/complaints/"+complaintPublicId+"/conversations/#lastMessage";
		}
		
		
	}
	
	@PostMapping("/collaborators/complaints/{publicId}/conversations/messages/image")
	public String saveCollaboratorMessageImage(@PathVariable("publicId") String complaintPublicId,@RequestParam("image") MultipartFile image) 
	{  
		
		if(messageService.saveCollaboratorMessageImage(image,complaintPublicId)==null)
		{
			return "collaboratorNotFoundResource";
		}
		else
		{
			return "redirect:/collaborators/complaints/"+complaintPublicId+"/conversations/#lastMessage";
		}
		
	}
	

	
	
	
	
	
	
	
}
