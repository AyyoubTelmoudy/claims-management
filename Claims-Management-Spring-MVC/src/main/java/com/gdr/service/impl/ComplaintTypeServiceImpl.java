package com.gdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdr.entities.ComplaintType;
import com.gdr.repositories.ComplaintTypeRepository;
import com.gdr.services.ComplaintTypeService;
import com.gdr.shared.Utils;
import com.gdr.dto.ComplaintTypeDto;

@Service
public class ComplaintTypeServiceImpl  implements ComplaintTypeService{

	@Autowired
	ComplaintTypeRepository typeReclamationRepository;

	@Override
	public List<ComplaintTypeDto> getAllTypes() {
		List<ComplaintType> complaintTypes=(List<ComplaintType>) typeReclamationRepository.findAll();
		List<ComplaintTypeDto> complaintTypesDto=new ArrayList<ComplaintTypeDto>();
		for(ComplaintType complaintType : complaintTypes)
		{
			ComplaintTypeDto complaintTypeDto=new ComplaintTypeDto();
			BeanUtils.copyProperties(complaintType,complaintTypeDto);
			complaintTypesDto.add(complaintTypeDto);
		}
        return complaintTypesDto;
	}


	@Override
	public void saveComplaintType(ComplaintTypeDto complaintTypeDto) {
		ComplaintType complaintType=new ComplaintType();
		
		complaintType.setName(complaintTypeDto.getName());
		complaintType.setPublicId(Utils.genereteRandomString(30));
		typeReclamationRepository.save(complaintType);
	}


	@Override
	public ComplaintTypeDto getComplaintType(String publicId) {
		ComplaintTypeDto complaintTypeDto=new ComplaintTypeDto();
		ComplaintType complaintType=typeReclamationRepository.findByPublicId(publicId);
		if(complaintType==null)
		{
			return null;
		}
		else
		{
			BeanUtils.copyProperties(complaintType,complaintTypeDto);
			return complaintTypeDto;
		}

	}


	@Override
	public void updateComplaintType(ComplaintTypeDto complaintTypeDto) {
		ComplaintType complaintType=typeReclamationRepository.findByPublicId(complaintTypeDto.getPublicId());
		complaintType.setName(complaintTypeDto.getName());
		typeReclamationRepository.save(complaintType);
		
	}
	@Override
	public ComplaintTypeDto deleteComplaintType(String publicId) {
		ComplaintType complaintType=typeReclamationRepository.findByPublicId(publicId);
		if(complaintType==null)
		{
			return null;
		}
		else
		{
			ComplaintTypeDto complaintTypeDto=new ComplaintTypeDto();
			BeanUtils.copyProperties(complaintType,complaintTypeDto);
			typeReclamationRepository.delete(complaintType);
			return complaintTypeDto;
		}
	}
	

}
