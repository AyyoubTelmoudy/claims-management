package com.gdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gdr.constants.ResourcesConstants;
import com.gdr.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gdr.entities.Collaborator;
import com.gdr.repositories.CollaboratorRepository;
import com.gdr.repositories.UserRepository;
import com.gdr.services.CollaboratorService;
import com.gdr.shared.Utils;
import com.gdr.dto.CollaboratorDto;
import com.gdr.dto.PasswordUpdateDto;


@Service
public class CollaboratorServiceImpl implements CollaboratorService{
	
	@Autowired
	CollaboratorRepository collaboratorRepository  ;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SharedService sharedService;
	
	@Override
	public List<CollaboratorDto> getAllCollaborators() {
		
		List<Collaborator> collaborators=(List<Collaborator>) collaboratorRepository.findAll();
		List<CollaboratorDto> collaboratorsDto=new ArrayList<CollaboratorDto>();
		
		for(Collaborator collaborator : collaborators)
		{
			CollaboratorDto collaboratorDto=new CollaboratorDto();
			BeanUtils.copyProperties(collaborator,collaboratorDto);
			collaboratorsDto.add(collaboratorDto);
		}
        return collaboratorsDto;
	}

	@Override
	public void saveCollaborator(CollaboratorDto collaboratorDto) {
		
		User collaboratorUser=new User();
		collaboratorUser.setLogin(collaboratorDto.getEmail());
		collaboratorUser.setEncryptedPassword(bCryptPasswordEncoder.encode(ResourcesConstants.DEFAULT_PASSWORD));
		collaboratorUser.setPublicId(Utils.genereteRandomString(30));
		collaboratorUser.setRole("ROLE_COLLABORATOR");
		collaboratorUser.setStatus(collaboratorDto.getStatus());
		User savedcollaboratorUserUser=userRepository.save(collaboratorUser);	
		Collaborator collaborator=new Collaborator();
		collaborator.setEmail(collaboratorDto.getEmail());
		collaborator.setFirstName(collaboratorDto.getFirstName());
		collaborator.setLastName(collaboratorDto.getLastName());
		collaborator.setStatus(collaboratorDto.getStatus());
		collaborator.setPhoneNumber(collaboratorDto.getPhoneNumber());
		collaborator.setPublicId(Utils.genereteRandomString(30));
		collaborator.setUser(savedcollaboratorUserUser);
		collaboratorRepository.save(collaborator);
			
	}

	@Override
	public CollaboratorDto getCollaborator(String publicId) {
		Collaborator collaborator=collaboratorRepository.findByPublicId(publicId);
		if(collaborator==null)
		{
			return null;
		}
		else
		{
			CollaboratorDto collaboratorDto=new CollaboratorDto();
			BeanUtils.copyProperties(collaborator,collaboratorDto);
			return collaboratorDto;
		}	
	}

	
	@Override
	public void updateCollaborator(CollaboratorDto collaboratorDto) {
		Collaborator collaborator=collaboratorRepository.findByPublicId(collaboratorDto.getPublicId());
		collaborator.setEmail(collaboratorDto.getEmail());
		collaborator.setFirstName(collaboratorDto.getFirstName());
		collaborator.setLastName(collaboratorDto.getLastName());
		collaborator.setPhoneNumber(collaboratorDto.getPhoneNumber());
		collaborator.setStatus(collaboratorDto.getStatus());
		collaboratorRepository.save(collaborator);
		User collaboratorUser=collaborator.getUser();
		collaboratorUser.setStatus(collaboratorDto.getStatus());
		collaboratorUser.setLogin(collaboratorDto.getEmail());
		userRepository.save(collaboratorUser);
	}
	@Override
	public CollaboratorDto deleteCollaborator(String publicId) {
		Collaborator collaborator=collaboratorRepository.findByPublicId(publicId);
		if(collaborator==null)
		{
			return null;
		}
		else
		{
			User collaboratorUser=collaborator.getUser();
			CollaboratorDto collaboratorDto=new CollaboratorDto();
			BeanUtils.copyProperties(collaborator,collaboratorDto);
			collaboratorRepository.delete(collaborator);
			userRepository.delete(collaboratorUser);
			return collaboratorDto;
		}
	}

	@Override
	public void updateCollaboratorPassword(PasswordUpdateDto passwordUpdateDto) {
		User collaboratorUser=sharedService.getAuthenticatedCollaborator().getUser();
		collaboratorUser.setEncryptedPassword(bCryptPasswordEncoder.encode(passwordUpdateDto.getNewPassword()));
		 	userRepository.save(collaboratorUser);
	}

	@Override
	public CollaboratorDto blockCollaborator(CollaboratorDto collaboratorDto) {
		Collaborator collaborator=collaboratorRepository.findByPublicId(collaboratorDto.getPublicId());
		if(collaborator==null)
		{
			return null;
		}
		else
		{
			collaborator.setStatus("BLOCKED");
			collaboratorRepository.save(collaborator);
			return  collaboratorDto;
		}
	}
		


}
