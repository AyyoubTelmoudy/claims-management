package com.gdr.services;

import java.util.List;

import com.gdr.dto.CollaboratorDto;
import com.gdr.dto.PasswordUpdateDto;

public interface CollaboratorService {

	List<CollaboratorDto> getAllCollaborators();

	void saveCollaborator(CollaboratorDto collaboratorDto);

	CollaboratorDto getCollaborator(String publicId);

	void updateCollaborator(CollaboratorDto collaboratorDto);

	CollaboratorDto deleteCollaborator(String publicId);

	void updateCollaboratorPassword(PasswordUpdateDto passwordUpdateDto);

	CollaboratorDto blockCollaborator(CollaboratorDto collaborator);


}
