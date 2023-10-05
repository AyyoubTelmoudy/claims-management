package com.gdr.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.gdr.constants.ResourcesConstants;
import com.gdr.entities.ClientProject;
import com.gdr.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gdr.entities.Client;
import com.gdr.entities.Project;
import com.gdr.repositories.ClientProjectRepository;
import com.gdr.repositories.ClientRepository;
import com.gdr.repositories.CollaboratorRepository;
import com.gdr.repositories.ProjectRepository;
import com.gdr.repositories.UserRepository;
import com.gdr.services.ClientService;
import com.gdr.shared.Utils;
import com.gdr.dto.ClientDto;
import com.gdr.dto.PasswordUpdateDto;


@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CollaboratorRepository collaboratorRepository;
	@Autowired
	ProjectRepository projectRepository ;
	@Autowired
	ClientProjectRepository clientProjectRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	SharedService sharedService;
	
	@Override
    public List<ClientDto> getAllClients(){
		
		List<Client> clients=(List<Client>) clientRepository.findAll();
		List<ClientDto> clientsDto=new ArrayList<ClientDto>();
		
		for(Client client : clients)
		{
			ClientDto clientDto=new ClientDto();
			BeanUtils.copyProperties(client,clientDto);
			clientsDto.add(clientDto);
		}
        return clientsDto;
    }
	
	@Override 
	public void saveClient(ClientDto clientDto) {
	
		User clientUser=new User();
		clientUser.setLogin(clientDto.getEmail());
		clientUser.setEncryptedPassword(bCryptPasswordEncoder.encode(ResourcesConstants.DEFAULT_PASSWORD));
		clientUser.setPublicId(Utils.genereteRandomString(30));
		clientUser.setRole("ROLE_CLIENT");
		clientUser.setStatus(clientDto.getStatus());
		User savedClientUser=userRepository.save(clientUser);

		Client client=new Client();
		client.setUser(savedClientUser);
		client.setEmail(clientDto.getEmail());
		client.setSocialReason(clientDto.getSocialReason());
		client.setPhoneNumber(clientDto.getPhoneNumber());
		client.setPublicId(Utils.genereteRandomString(30));
		client.setStatus(clientDto.getStatus());
		Client savedClient=clientRepository.save(client);	
		
		Project project=new Project();
		project.setPublicId(Utils.genereteRandomString(30));
		project.setName(clientDto.getProjectName());
		project.setStatus("Actif");
		Project savedProject=projectRepository.save(project);
		
		ClientProject clientProject=new ClientProject ();
		clientProject.setClient(savedClient);
		clientProject.setCollaborator(collaboratorRepository.findByEmail(clientDto.getCollaboratorEmail()));
		clientProject.setPublicId(Utils.genereteRandomString(30));
		clientProject.setAssignmentDate(LocalDate.now());
		clientProject.setProject(savedProject);
		clientProjectRepository.save(clientProject);
	}
	@Override
	public ClientDto getClient(String publicId) {
		ClientDto clientDto=new ClientDto();
		Client client=new Client();
		client=clientRepository.findByPublicId(publicId);
		if(client==null)
		{
			return null;
		}
		else
		{
			BeanUtils.copyProperties(client,clientDto);
			return clientDto;
		}
	}

	@Override
	public void updateClient(ClientDto clientDto) {
		Client client=clientRepository.findByPublicId(clientDto.getPublicId());
		client.setEmail(clientDto.getEmail());
		client.setSocialReason(clientDto.getSocialReason());
		client.setPhoneNumber(clientDto.getPhoneNumber());
		client.setStatus(clientDto.getStatus());
		clientRepository.save(client);
		User clientUser=client.getUser();
		clientUser.setStatus(clientDto.getStatus());
		clientUser.setLogin(clientDto.getEmail());
		userRepository.save(clientUser);
		
	}

	
	@Override
	public ClientDto blockClient(ClientDto clientDto) {
		Client client=clientRepository.findByPublicId(clientDto.getPublicId());
		if(client==null)
		{
			return null;
		}
		else
		{
			client.setStatus("BLOCKED");
			clientRepository.save(client);
			User clientUser=client.getUser();
			clientUser.setStatus("BLOCKED");
			userRepository.save(clientUser);
			return clientDto;
		}
	}

	@Override
	public ClientDto deleteClient(String publicId) {
        Client client=clientRepository.findByPublicId(publicId);
		if(client==null)
		{
			return null;
		}
		else
		{
			ClientDto clientDto=new ClientDto ();
	        User clientUser=client.getUser();
	        BeanUtils.copyProperties(client,clientDto);
			clientRepository.delete(client);
			userRepository.delete(clientUser);
			return clientDto;
		}
		
	}
	@Override
	public void updateClientPssword(PasswordUpdateDto passwordUpdateDto) {
		User clientUser=sharedService.getAuthenticatedClient().getUser();
		     clientUser.setEncryptedPassword(bCryptPasswordEncoder.encode(passwordUpdateDto.getNewPassword()));
		 	userRepository.save(clientUser);
	}
	

	
	
	
}
