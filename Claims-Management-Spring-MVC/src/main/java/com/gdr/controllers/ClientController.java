package com.gdr.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdr.service.impl.SharedService;
import com.gdr.shared.Utils;
import com.gdr.dto.ClientDto;
import com.gdr.dto.CollaboratorDto;
import com.gdr.dto.PasswordUpdateDto;
import com.gdr.dto.ProjectDto;
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
import com.gdr.services.ProjectService;

@Controller
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	ClientService clientService;
	@Autowired
	CollaboratorService collaboratorService;
	@Autowired
	ProjectService projectService;
	@Autowired
    SharedService sharedService;
	
	private String randomString="7yIUYiuy9789oiUIOU8ou7897UiouIOUio";
	
	//Get all clients
	@GetMapping("/list")
	public String getAllClients(Model model) {	
		List<ClientDto> clientsDto=clientService.getAllClients();
		model.addAttribute("clientsDto",clientsDto);
		return "listClients";
	}
	//Create a new client
	@GetMapping("/new")
	public String createClient(Model model) {
		
		List<CollaboratorDto> collaboratorsDto=collaboratorService.getAllCollaborators();
		ClientDto clientDto=new ClientDto();
		clientDto.setStatus("BLOCKED");
		model.addAttribute("clientDto",clientDto);
		model.addAttribute("collaboratorsDto",collaboratorsDto);
		return "createClient";
	}
	//Save a new client
	@PostMapping("/save")
	public String saveClient(@ModelAttribute("clientDto") ClientDto clientDto) {
		clientService.saveClient(clientDto);
		return "redirect:/clients/list";
	}
	
	//Edit client
	@GetMapping("/edit/{publicId}")
	public String editClient(Model model,@PathVariable String publicId) {	
		ClientDto clientDto=clientService.getClient(publicId);
		model.addAttribute("clientDto",clientDto);
		if(clientDto==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			return "editClient";
		}
	}
	@PostMapping("/update")
	public String updateClient(@ModelAttribute("clientDto") ClientDto clientDto) {
		clientService.updateClient(clientDto);
		return "redirect:/clients/list";	
	}
	
	@GetMapping("/password/update/form")
	public String updateClientPsswordForm(HttpServletRequest request,Model model) {
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
			randomString= Utils.genereteRandomString(30);
		}
		else
		{
			model.addAttribute("passwordUpdated","false");
			model.addAttribute("passwordUpdateDto",new PasswordUpdateDto());
		}
    	return "clientPasswordUpdateForm";
	} 
	@PostMapping("/password/update")
	public String updateClientPssword(HttpServletResponse response,@ModelAttribute("passwordUpdateDto") PasswordUpdateDto passwordUpdateDto) throws IOException {	
		clientService.updateClientPssword(passwordUpdateDto);
		randomString=Utils.genereteRandomString(30);
		Cookie cookie=new Cookie(randomString,"updated");
		response.addCookie(cookie);
	    return "redirect:/clients/password/update/form";
	}
	
	//Update client
	@GetMapping("/block/{publicId}")
	public String blockClient(@PathVariable String publicId) {
		ClientDto clientDto=clientService.blockClient(clientService.getClient(publicId));
		if(clientDto==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			return "redirect:/clients/list";
		}	
	}

	
	//Delete client
	@GetMapping("/delete/{publicId}")
	public String deleteClient(@PathVariable String publicId) {
		ClientDto clientDto=clientService.deleteClient(publicId);
		if(clientDto==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			return "redirect:/clients/list";	
		}
	}

	
	@GetMapping("/projects/list")
	public String getClientProjects(Model model) {
		List<ProjectDto> projectsDto=projectService.getClientProjects(sharedService.getAuthenticatedClient());
		model.addAttribute("projectsDto",projectsDto);
		return "clientProjects";	
	}
	
	
}
