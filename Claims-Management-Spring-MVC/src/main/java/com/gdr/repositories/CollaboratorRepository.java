package com.gdr.repositories;

import com.gdr.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gdr.entities.Collaborator;


@Repository
public interface CollaboratorRepository extends CrudRepository<Collaborator,Long> {

	Collaborator findByPublicId(String publicId);

    Collaborator findByEmail(String collaboratorEmail);

	Collaborator findByUser(User user);


}