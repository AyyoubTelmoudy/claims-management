package com.gdr.repositories;


import com.gdr.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

	User findByLogin(String login);
	User findByPublicId(String publicId);
}