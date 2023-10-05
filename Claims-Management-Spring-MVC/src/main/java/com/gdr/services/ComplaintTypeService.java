package com.gdr.services;

import java.util.List;

import com.gdr.dto.ComplaintTypeDto;

public interface ComplaintTypeService {

	List<ComplaintTypeDto> getAllTypes();

	void saveComplaintType(ComplaintTypeDto complaintTypeDto);

	ComplaintTypeDto getComplaintType(String publicId);

	void updateComplaintType(ComplaintTypeDto complaintTypeDto);

	ComplaintTypeDto deleteComplaintType(String publicId);

}
