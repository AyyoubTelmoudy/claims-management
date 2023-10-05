package com.gdr.controllers;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gdr.services.ClientService;
import com.gdr.services.CollaboratorService;
import com.gdr.services.ComplaintService;
import com.gdr.services.SupervisorService;
import com.gdr.dto.ClientDto;
import com.gdr.dto.CollaboratorDto;
import com.gdr.dto.ComplaintDto;
import com.gdr.dto.PasswordUpdateDto;
import com.gdr.dto.ProjectAssignmentDto;
import com.gdr.dto.SupervisorDto;


@Controller
@RequestMapping("/supervisors")
public class SupervisorController {

	@Autowired
	SupervisorService supervisorService;
	@Autowired
	ClientService clientService;
	@Autowired
	CollaboratorService collaboratorService;
	@Autowired
	ComplaintService complaintService;
	private String randomString="7yIUYiuy9789oiUIOU8ou7897wsuhsuwhus7UiouIOUio";
	
	
	@GetMapping("/list")
	public String getAllSupervisors(Model model) {	
		List<SupervisorDto> supervisorsDto=supervisorService.getAllSupervisors();
		model.addAttribute("supervisorsDto",supervisorsDto);
		return "supervisorsList";
	}
	
	@GetMapping("/new")
	public String createSupervisor(Model model) {
		
		SupervisorDto supervisorDto=new SupervisorDto();
		supervisorDto.setStatus("BLOCKED");
		model.addAttribute("supervisorDto",supervisorDto);
		return "createSupervisor";
	}
	
	@PostMapping("/save")
	public String saveClient(@ModelAttribute("supervisorDto") SupervisorDto supervisorDto) {
		supervisorService.saveSupervisor(supervisorDto);
		return "redirect:/supervisors/list";
	}
	
	
	@GetMapping("/edit/{publicId}")
	public String editSupervisor(Model model,@PathVariable String publicId) {	
		SupervisorDto supervisorDto=supervisorService.getSupervisor(publicId);
		model.addAttribute("supervisorDto",supervisorDto);
		if(supervisorDto==null)
		{
			return "adminNotFoundResource";
		}
		else
		{
			return "editSupervisor";
		}
	}
	@PostMapping("/update")
	public String updateSupervisor(@ModelAttribute("supervisorDto") SupervisorDto supervisorDto) {
		supervisorService.updateSupervisor(supervisorDto);
		return "redirect:/supervisors/list";	
	}
	
	@GetMapping("/delete/{publicId}")
	public String deleteSupervisor(@PathVariable String publicId) {
		SupervisorDto supervisorDto=supervisorService.deleteSupervisort(publicId);
		if(supervisorDto==null)
		{
			return "adminNotFoundResource";
		}
		else
		{
			return "redirect:/supervisors/list";	
		}
	}
	
	@GetMapping("/block/{publicId}")
	public String blockSupervisor(@PathVariable String publicId) {
		SupervisorDto supervisorDto=supervisorService.blockSupervisor(supervisorService.getSupervisor(publicId));
		if(supervisorDto==null)
		{
			return "adminNotFoundResource";
		}
		else
		{
			return "redirect:/supervisors/list";
		}	
	}
	
	@GetMapping("/projects/assign/form")
	public String assignProjectForm(Model model) {
		List<ClientDto> clientsDto=clientService.getAllClients();
		List<CollaboratorDto> collaboratorsDto=collaboratorService.getAllCollaborators();
		model.addAttribute("projectAssignmentDto",new ProjectAssignmentDto());
		model.addAttribute("clientsDto",clientsDto);
		model.addAttribute("collaboratorsDto",collaboratorsDto);
		return "assignProject";
	}
	@PostMapping("/projects/assign")
	public String assignProject(Model model,@ModelAttribute("projectAssignmentDto") ProjectAssignmentDto projectAssignmentDto) {
		supervisorService.assignProject(projectAssignmentDto);
		return "redirect:/projects/list";
	}
	@GetMapping("/collaborators/complaints/details/{publicId}")
	public String complaintDetails(@PathVariable String publicId,Model model) {	
		ComplaintDto complaintDto=complaintService.getComplaint(publicId);
		if(complaintDto==null)
		{
			return "supervisorNotFoundResource";	
		}
		else
		{
			model.addAttribute("complaintDto",complaintDto);
			return "complaintDetails";	
		}
	}
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/password/update/form")
	public String updateSupervisorPsswordForm(HttpServletRequest request,Model model) {
		boolean existsCookies=false;
		Cookie[] cookies = request.getCookies();
		String cookieName = randomString;
		for ( int i=0; i<cookies.length; i++) {
		      Cookie cookie = cookies[i];
		      if (cookieName.equals(cookie.getName()))
		    	  existsCookies=true;
		    }
		if(existsCookies)
		{
			model.addAttribute("passwordUpdated","true");
			randomString=genereteRandomString(30);	
		}
		else
		{
			model.addAttribute("passwordUpdated","false");
			model.addAttribute("passwordUpdateDto",new PasswordUpdateDto());
		}
    	return "supervisorPasswordUpdateForm";
	} 
	@PostMapping("/password/update")
	public String updateSupervisorPssword(HttpServletResponse response,@ModelAttribute("passwordUpdateDto") PasswordUpdateDto passwordUpdateDto) throws IOException {	
		//supervisorService.updatesupervisorPssword(passwordUpdateDto);
		randomString=genereteRandomString(30);
		Cookie cookie=new Cookie(randomString,"updated");
		response.addCookie(cookie);
	    return "redirect:/supervisors/password/update/form";
	}
	
	private final Random RANDOM=new SecureRandom();
    private final String ALPHANUM="01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz";
    public String genereteRandomString(int length)
    {
    	StringBuilder returnvalue=new StringBuilder(length);
    	for (int i = 0; i < length; i++) {
			returnvalue.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
		}
    	return new String(returnvalue);
    }
	
	
}
