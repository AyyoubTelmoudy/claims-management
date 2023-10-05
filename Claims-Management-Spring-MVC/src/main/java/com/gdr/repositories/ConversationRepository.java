package com.gdr.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gdr.entities.Complaint;
import com.gdr.entities.Conversation;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation,Long>{

	Conversation findByComplaint(Complaint complaint);

	Conversation findByPublicId(String publicId);

}
