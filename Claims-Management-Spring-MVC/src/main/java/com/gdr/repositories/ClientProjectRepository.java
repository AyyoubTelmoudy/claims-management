package com.gdr.repositories;

import java.util.List;

import com.gdr.entities.ClientProject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gdr.entities.Client;
import com.gdr.entities.Collaborator;
import com.gdr.entities.Project;
@Repository
public interface ClientProjectRepository extends CrudRepository<ClientProject,Long> {
  
    List<ClientProject> findByClient(Client client);

	ClientProject findByProject(Project project);

	List<ClientProject> findByCollaborator(Collaborator collaborator);



}