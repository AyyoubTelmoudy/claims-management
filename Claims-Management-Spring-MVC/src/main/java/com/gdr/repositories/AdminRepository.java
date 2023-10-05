package com.gdr.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.gdr.entities.Admin;


@Repository
public interface AdminRepository extends CrudRepository<Admin,Long> {

	Admin findByLogin(String login);


}