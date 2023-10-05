package com.gdr.repositories;

import java.util.List;

import com.gdr.entities.ClientProject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gdr.entities.Complaint;
@Repository
public interface ComplaintRepository extends CrudRepository<Complaint,Long> {

	List<Complaint> findByClientProject(ClientProject clientProjet);

	Complaint findByPublicId(String publicId);


}