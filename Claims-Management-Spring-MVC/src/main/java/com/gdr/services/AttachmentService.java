package com.gdr.services;

import com.gdr.dto.AttachmentDto;

public interface AttachmentService {

	AttachmentDto getComplaintAttachmentByConversationPublicId(String publicId);

	AttachmentDto getComplaintAttachmentByComplaintPublicId(String publicId);

}
