package com.gdr.repositories;

import com.gdr.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.gdr.entities.Client;


@Repository
public interface ClientRepository extends CrudRepository<Client,Long> {

	Client findByPublicId(String publicId);

	Client findByEmail(String email);

	Client findByUser(User user);
}