package com.gdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gdr.entities.ClientProject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdr.entities.Client;
import com.gdr.entities.Collaborator;
import com.gdr.entities.Project;
import com.gdr.repositories.ClientProjectRepository;
import com.gdr.repositories.ProjectRepository;
import com.gdr.services.ProjectService;
import com.gdr.shared.Utils;
import com.gdr.dto.ProjectDto;
@Service
public class ProjectServiceImpl implements ProjectService {

	
	@Autowired
	ClientProjectRepository clientProjetRepository;
	@Autowired
	ProjectRepository projetRepository;


	@Override
	public List<ProjectDto> getAllProjects() {
		List<Project> projects=(List<Project>) projetRepository.findAll();
		List<ProjectDto> projectsDto=new ArrayList<ProjectDto>();
		for(Project project : projects)
		{
			ProjectDto projectDto=new ProjectDto();
			BeanUtils.copyProperties(project,projectDto);
			ClientProject clientProject=clientProjetRepository.findByProject(project);
			projectDto.setClientSocialReason(clientProject.getClient().getSocialReason());
			projectDto.setCollaboratorEmail(clientProject.getCollaborator().getEmail());
			projectsDto.add(projectDto);
		}
        return projectsDto;
	}

	@Override
	public void saveProject(ProjectDto projectDto) {
		Project project=new Project();
		project.setName(projectDto.getName());
		project.setPublicId(Utils.genereteRandomString(30));
		project.setStatus(projectDto.getStatus());
		projetRepository.save(project);	
	}

	@Override
	public ProjectDto getProject(String publicId) {		
		ProjectDto projectDto=new ProjectDto();
		Project project=projetRepository.findByPublicId(publicId);
		if(project==null)
		{
			return null;
		}
		else
		{
			BeanUtils.copyProperties(project,projectDto);
			projectDto.setClientSocialReason(clientProjetRepository.findByProject(project).getClient().getSocialReason());
			projectDto.setCollaboratorEmail(clientProjetRepository.findByProject(project).getCollaborator().getEmail());
			return projectDto;
		}
	}

	@Override
	public void updateProject(ProjectDto projectDto) {
		
		Project project=projetRepository.findByPublicId(projectDto.getPublicId());
		project.setName(projectDto.getName());
		project.setStatus(projectDto.getStatus());
		projetRepository.save(project);
		
	}

	@Override
	public ProjectDto deleteProject(String publicId) {
		Project project=projetRepository.findByPublicId(publicId);
		if(project==null)
		{
			return null;
		}
		else
		{
			ProjectDto projectDto=new ProjectDto();
			BeanUtils.copyProperties(project, projectDto);
			projetRepository.delete(project);
			return projectDto;
		}
	}
	@Override
	public List<ProjectDto> getClientProjects(Client client) {
		List<ClientProject> listClientProjets=clientProjetRepository.findByClient(client);	
		List<ProjectDto> listProjetsDto=new ArrayList<ProjectDto>();	
        for (ClientProject clientProjet : listClientProjets) {
			ProjectDto projectDto=new ProjectDto();
			Project project=clientProjet.getProject();
			BeanUtils.copyProperties(project,projectDto);
			projectDto.setClientSocialReason(clientProjet.getClient().getSocialReason());
			projectDto.setCollaboratorEmail(clientProjet.getCollaborator().getEmail());
			listProjetsDto.add(projectDto);
        }
        return listProjetsDto;
	}

	@Override
	public List<ProjectDto> getCollaboratorProjects(Collaborator authentificatedCollaborator) {
		List<ClientProject> listClientProjets=clientProjetRepository.findByCollaborator(authentificatedCollaborator);	
		List<ProjectDto> listProjetsDto=new ArrayList<ProjectDto>();	
        for (ClientProject clientProjet : listClientProjets) {
			ProjectDto projectDto=new ProjectDto();
			Project project=clientProjet.getProject();
			BeanUtils.copyProperties(project,projectDto);
			projectDto.setClientSocialReason(clientProjet.getClient().getSocialReason());
			listProjetsDto.add(projectDto);
        }
        return listProjetsDto;
	}



}
