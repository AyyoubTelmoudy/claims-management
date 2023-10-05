package com.gdr.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gdr.entities.Conversation;
import com.gdr.entities.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long>{

	List<Message> findByConversation(Conversation conversation);

}
