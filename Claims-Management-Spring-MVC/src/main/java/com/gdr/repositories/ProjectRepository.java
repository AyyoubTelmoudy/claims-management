package com.gdr.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gdr.entities.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {


	//Project findByName(String name);

	Project findByPublicId(String publicId);
	
}