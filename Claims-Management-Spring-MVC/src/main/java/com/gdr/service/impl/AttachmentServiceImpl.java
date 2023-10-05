package com.gdr.service.impl;

import java.util.List;

import com.gdr.entities.Attachment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdr.entities.Complaint;
import com.gdr.repositories.AttachmentRepository;
import com.gdr.repositories.ComplaintRepository;
import com.gdr.repositories.ConversationRepository;
import com.gdr.services.AttachmentService;
import com.gdr.dto.AttachmentDto;

@Service
public class AttachmentServiceImpl implements AttachmentService{

	@Autowired
	ConversationRepository conversationRepository;
	@Autowired
	AttachmentRepository attachmentRepository;
	@Autowired
	ComplaintRepository complaintRepository;
	
	@Override
	public AttachmentDto getComplaintAttachmentByConversationPublicId(String publicId) {
		Complaint complaint=conversationRepository.findByPublicId(publicId).getComplaint();
		List<Attachment> attachments=attachmentRepository.findByComplaint(complaint);
		if(attachments.size()>0)
		{
			System.out.println(attachments.size());
			AttachmentDto attachmentDto=new AttachmentDto();
			BeanUtils.copyProperties(attachments.get(0),attachmentDto);
			return attachmentDto;
		}
        return null;
	}
	
	@Override
	public AttachmentDto getComplaintAttachmentByComplaintPublicId(String publicId) {
		
		Complaint complaint=complaintRepository.findByPublicId(publicId);
		List<Attachment> attachments=attachmentRepository.findByComplaint(complaint);
		if(attachments.size()>0) 
		{
			AttachmentDto attachmentDto=new AttachmentDto();
			BeanUtils.copyProperties(attachments.get(0),attachmentDto);
			return attachmentDto;
		}
		return null;

		
	}
	

}
