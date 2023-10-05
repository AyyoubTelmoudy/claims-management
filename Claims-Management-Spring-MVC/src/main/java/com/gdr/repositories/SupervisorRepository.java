package com.gdr.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.gdr.entities.Supervisor;

@Repository
public interface SupervisorRepository extends CrudRepository<Supervisor,Long> {
	Supervisor findByLogin(String login);

	Supervisor findByEmail(String clientEmail);

	Supervisor findByPublicId(String publicId);

}