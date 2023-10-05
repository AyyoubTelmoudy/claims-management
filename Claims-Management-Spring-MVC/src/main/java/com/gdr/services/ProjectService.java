package com.gdr.services;

import java.util.List;

import com.gdr.dto.ProjectDto;
import com.gdr.entities.Client;
import com.gdr.entities.Collaborator;


public interface ProjectService {

	List<ProjectDto> getAllProjects();

	void saveProject(ProjectDto projectDto);

	ProjectDto getProject(String publicId);

	void updateProject(ProjectDto projectDto);

	ProjectDto deleteProject(String publicId);

	List<ProjectDto> getClientProjects(Client client);

	List<ProjectDto> getCollaboratorProjects(Collaborator authentificatedCollaborator);


}
