package com.gdr.services;

import java.util.List;

import com.gdr.dto.ComplaintDto;
import org.springframework.web.multipart.MultipartFile;

import com.gdr.entities.Client;

public interface ComplaintService {
	
	void saveComplaint(ComplaintDto complaintDto, MultipartFile pieceJoint);
	public List<ComplaintDto> clientComplaints(Client client);
	long getNextTicketNum(Client client);
	List<ComplaintDto> getAllComplaints();
	ComplaintDto getComplaint(String publicId);
	ComplaintDto getComplaintByConversationPublicId(String publicId);
	List<ComplaintDto> getCollaboratorComplaints();
	ComplaintDto closeComplaint(String publicId);
	void setConsultedByCollaborator(String publicId, boolean b);

}
