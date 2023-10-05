package com.gdr.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gdr.constants.ResourcesConstants;
import com.gdr.entities.ClientProject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gdr.entities.Client;
import com.gdr.entities.Collaborator;
import com.gdr.entities.Project;
import com.gdr.entities.Supervisor;
import com.gdr.repositories.ClientProjectRepository;
import com.gdr.repositories.ClientRepository;
import com.gdr.repositories.CollaboratorRepository;
import com.gdr.repositories.ProjectRepository;
import com.gdr.repositories.SupervisorRepository;
import com.gdr.repositories.UserRepository;
import com.gdr.services.SupervisorService;
import com.gdr.shared.Utils;
import com.gdr.dto.PasswordUpdateDto;
import com.gdr.dto.ProjectAssignmentDto;
import com.gdr.dto.SupervisorDto;

@Service
public class SupervisorServiceImpl implements SupervisorService {
    
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	CollaboratorRepository collaboratorRepository ;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ClientProjectRepository clientProjectRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	SharedService sharedService;
	@Autowired
	SupervisorRepository supervisorRepository;

	
	@Override
	public void assignProject(ProjectAssignmentDto projectAssignmentDto) {
		
		Client client=clientRepository.findByEmail(projectAssignmentDto.getClientEmail());		
		Collaborator collaborator=collaboratorRepository.findByEmail(projectAssignmentDto.getCollaboratorEmail());	
		
		Project project=new Project();
		project.setName(projectAssignmentDto.getProjectName());
		project.setPublicId(Utils.genereteRandomString(30));
		project.setStatus("New project");
		Project savedProject=projectRepository.save(project);
		
		ClientProject clientProject=new ClientProject();
		clientProject.setAssignmentDate(LocalDate.now());
		clientProject.setCollaborator(collaborator);
		clientProject.setClient(client);
		clientProject.setProject(savedProject);
		clientProject.setPublicId(Utils.genereteRandomString(30));
		clientProjectRepository.save(clientProject);
	}

	@Override
	public void updatesupervisorPssword(PasswordUpdateDto passwordUpdateDto) {
		Supervisor authenticatedSupervisor=sharedService.getAuthenticatedSupervisor();
		authenticatedSupervisor.setEncryptedPassword(bCryptPasswordEncoder.encode(passwordUpdateDto.getNewPassword()));
		supervisorRepository.save(authenticatedSupervisor);
	}

	@Override
	public List<SupervisorDto> getAllSupervisors() {
		List<Supervisor> supervisors=(List<Supervisor>) supervisorRepository.findAll();
		List<SupervisorDto> supervisorsDto=new ArrayList<SupervisorDto>();
		
		for(Supervisor supervisor : supervisors)
		{
			SupervisorDto supervisortDto=new SupervisorDto();
			BeanUtils.copyProperties(supervisor,supervisortDto);
			supervisorsDto.add(supervisortDto);
		}
        return supervisorsDto;
	}

	@Override
	public void saveSupervisor(SupervisorDto supervisorDto) {
		
		Supervisor supervisor=new Supervisor();
		supervisor.setEmail(supervisorDto.getEmail());
		supervisor.setFirstName(supervisorDto.getFirstName());
		supervisor.setLastName(supervisorDto.getLastName());
		supervisor.setPhoneNumber(supervisorDto.getPhoneNumber());
		supervisor.setStatus(supervisorDto.getStatus());
		supervisor.setEncryptedPassword(bCryptPasswordEncoder.encode(ResourcesConstants.DEFAULT_PASSWORD));
		supervisor.setPublicId(Utils.genereteRandomString(30));
		supervisor.setLogin(supervisorDto.getEmail());
		supervisorRepository.save(supervisor);
		
	}

	@Override
	public SupervisorDto getSupervisor(String publicId) {
		SupervisorDto supervisorDto=new SupervisorDto();
		Supervisor supervisor=new Supervisor();
		supervisor=supervisorRepository.findByPublicId(publicId);
		if(supervisor==null)
		{
			return null;
		}
		else
		{
			BeanUtils.copyProperties(supervisor,supervisorDto);
			return supervisorDto;
		}
	}

	@Override
	public void updateSupervisor(SupervisorDto supervisorDto) {
		Supervisor supervisor=supervisorRepository.findByPublicId(supervisorDto.getPublicId());
		supervisor.setEmail(supervisorDto.getEmail());
		supervisor.setPhoneNumber(supervisorDto.getPhoneNumber());
		supervisor.setStatus(supervisorDto.getStatus());
		supervisor.setFirstName(supervisorDto.getFirstName());
		supervisor.setLastName(supervisorDto.getLastName());
		supervisor.setLogin(supervisorDto.getEmail());
		supervisorRepository.save(supervisor);
	}
	@Override
	public SupervisorDto deleteSupervisort(String publicId) {
		Supervisor supervisor=supervisorRepository.findByPublicId(publicId);
		if(supervisor==null)
		{
			return null;
		}
		else
		{
			SupervisorDto supervisorDto=new SupervisorDto ();
	        BeanUtils.copyProperties(supervisor,supervisorDto);
	        supervisorRepository.delete(supervisor);
			return supervisorDto;
		}
	}

	@Override
	public SupervisorDto blockSupervisor(SupervisorDto supervisorDto) {
		Supervisor supervisor=supervisorRepository.findByPublicId(supervisorDto.getPublicId());
		if(supervisor==null)
		{
			return null;
		}
		else
		{
			supervisor.setStatus("BLOCKED");
			supervisorRepository.save(supervisor);
			return supervisorDto;
		}
	}

}
