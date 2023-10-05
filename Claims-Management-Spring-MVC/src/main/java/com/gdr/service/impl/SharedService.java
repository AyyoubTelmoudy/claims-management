package com.gdr.service.impl;

import com.gdr.entities.User;
import com.gdr.entities.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gdr.entities.Client;
import com.gdr.entities.Collaborator;
import com.gdr.entities.Supervisor;
import com.gdr.repositories.ClientRepository;
import com.gdr.repositories.CollaboratorRepository;
import com.gdr.repositories.SupervisorRepository;
import com.gdr.repositories.UserRepository;

@Service
public class SharedService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	CollaboratorRepository collaboratorRepository;
	@Autowired
	SupervisorRepository supervisorRepository;
	
    public User getAuthenticatedUser()
    {
		Authentication  authentication=SecurityContextHolder.getContext().getAuthentication();
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	    return userRepository.findByLogin(userDetails.getUsername());
    }  
    
    public Client getAuthenticatedClient()
    {
		Authentication  authentication=SecurityContextHolder.getContext().getAuthentication();
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	    return clientRepository.findByUser(userRepository.findByLogin(userDetails.getUsername()));
    } 
    
    public Supervisor getAuthenticatedSupervisor()
    {
		Authentication  authentication=SecurityContextHolder.getContext().getAuthentication();
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	    return supervisorRepository.findByLogin(userDetails.getUsername());
    } 
    
    public Collaborator getAuthenticatedCollaborator()
    {
		Authentication  authentication=SecurityContextHolder.getContext().getAuthentication();
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	    return collaboratorRepository.findByUser(userRepository.findByLogin(userDetails.getUsername()));
    } 
}
