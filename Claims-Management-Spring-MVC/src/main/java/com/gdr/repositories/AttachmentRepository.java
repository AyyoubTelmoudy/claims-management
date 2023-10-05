package com.gdr.repositories;

import java.util.List;

import com.gdr.entities.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gdr.entities.Complaint;
@Repository
public interface AttachmentRepository extends CrudRepository<Attachment,Long> {

	List<Attachment> findByComplaint(Complaint complaint);


}