package com.gdr.services;

import java.util.List;

import com.gdr.dto.PasswordUpdateDto;
import com.gdr.dto.ProjectAssignmentDto;
import com.gdr.dto.SupervisorDto;

public interface SupervisorService {

	void assignProject(ProjectAssignmentDto projectAssignmentDto);

	void updatesupervisorPssword(PasswordUpdateDto passwordUpdateDto);

	List<SupervisorDto> getAllSupervisors();

	void saveSupervisor(SupervisorDto supervisorDto);

	SupervisorDto getSupervisor(String publicId);

	void updateSupervisor(SupervisorDto supervisorDto);

	SupervisorDto deleteSupervisort(String publicId);

	SupervisorDto blockSupervisor(SupervisorDto supervisor);

}
