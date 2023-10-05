package com.gdr.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.gdr.entities.ComplaintType;

@Repository
public interface ComplaintTypeRepository extends CrudRepository<ComplaintType,Long> {

	ComplaintType findByName(String libelle);

	ComplaintType findByPublicId(String publicId);

}