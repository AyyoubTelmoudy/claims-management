package com.gdr.services;

import java.util.List;

import com.gdr.dto.ComplaintDto;
import com.gdr.dto.ConversationDto;
import com.gdr.dto.MessageDto;
import org.springframework.web.multipart.MultipartFile;

public interface MessageService {

	List<MessageDto> getConversationMessages(String publicId);
	ConversationDto saveClientMessage(MessageDto messagedto, String conversationPublicId);
	List<MessageDto> getComplaintConversationMessages(String publicId);
	ComplaintDto saveCollaboratorMessage(MessageDto newMessageDto, String complaintPublicId);
	ComplaintDto saveCollaboratorMessageImage(MultipartFile image, String complaintPublicId);
	ConversationDto saveClientMessageImage(MultipartFile image, String conversationPublicId);

}
