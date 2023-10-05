package com.gdr.services;

import java.util.List;


import com.gdr.responses.ComplaintResponse;
import com.gdr.dto.ConversationDto;

public interface ConversationService {

	List<ComplaintResponse> getResponses();

	ConversationDto getConversation(String publicId);

	ConversationDto getConversationByComplaintPublicId(String publicId);

	void setConsultedByClient(String publicId, boolean b);

	void setConsultedByCollaborator(String publicId, boolean b);

}
