package com.gdr.controllers;

import java.util.List;

import com.gdr.service.impl.SharedService;
import com.gdr.dto.ComplaintDto;
import com.gdr.dto.ComplaintTypeDto;
import com.gdr.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.gdr.entities.Client;
import com.gdr.services.ComplaintService;
import com.gdr.services.ComplaintTypeService;
import com.gdr.services.ProjectService;

@Controller
public class ComplaintController {

	@Autowired
	ComplaintService complaintService;
	@Autowired
	ComplaintTypeService complaintTypeService;
	@Autowired
	ProjectService projectService;
	@Autowired
    SharedService sharedService;
	
	@GetMapping("/clients/complaints/new")
	public String createComplaint(Model model) {
	    Client client=sharedService.getAuthenticatedClient();  
	    model.addAttribute("clientStatus",client.getUser().getStatus());
		ComplaintDto complaintDto= new ComplaintDto();
		model.addAttribute("complaintDto",complaintDto);
		long ticketNum=complaintService.getNextTicketNum(client);
		model.addAttribute("ticketNum",ticketNum);
		List<ComplaintTypeDto> complaintTypesDto=complaintTypeService.getAllTypes();
		model.addAttribute("complaintTypesDto",complaintTypesDto);
		List<ProjectDto> clientProjectsDto=projectService.getClientProjects(client);
		model.addAttribute("clientProjectsDto",clientProjectsDto);
		return "createComplaint";
	}
	@PostMapping("/clients/complaints")
	public String saveComplaint(@ModelAttribute("complaintDto") ComplaintDto complaintDto,@RequestParam("attachement") MultipartFile attachement) {
	    Client client=sharedService.getAuthenticatedClient();	    
	    complaintDto.setTicketNumber(complaintService.getNextTicketNum(client));
		complaintService.saveComplaint(complaintDto,attachement);
		return "redirect:/clients/complaints/new";
	}
	
	@GetMapping("/clients/complaints/list")
	public String listClientComplaints(Model model) {
	    Client client=sharedService.getAuthenticatedClient();	    
	    List<ComplaintDto> complaintsDto=complaintService.clientComplaints(client);
	    model.addAttribute("complaintsDto",complaintsDto);
		return "clientComplaintsList";
	}
	
	
	@GetMapping("/supervisors/collaborators/complaints/list")
	public String allClientsComplaint(Model model) {    
		    List<ComplaintDto> complaintsDto=complaintService.getAllComplaints();
		    model.addAttribute("complaintsDto",complaintsDto);
		return "collaboratorsComplaintsList";
	}
	
	@GetMapping("/clients/complaints/details/{publicId}")
	public String complaintDetails(@PathVariable String publicId,Model model) {	
		ComplaintDto  complaintDto=complaintService.getComplaint(publicId);
		if(complaintDto==null)
		{
			return "clientNotFoundResource";
		}
		else
		{
			model.addAttribute("complaintDto",complaintDto);
			return "clientComplaintDetails";
		}

	}	
	
	@GetMapping("/collaborators/complaints/list")
	public String collaboratorComplaints(Model model) {    
		    List<ComplaintDto> complaintsDto=complaintService.getCollaboratorComplaints();
		    model.addAttribute("complaintsDto",complaintsDto);
		return "collaboratorComplaintsList";
	}	
	
	@GetMapping("/collaborators/complaints/{publicId}/close")
	public String collaboratorComplaints(@PathVariable String publicId) {	
		if(complaintService.closeComplaint(publicId)==null)
		{
			return "collaboratorNotFoundResource";
		}
		else
		{
			return "redirect:/collaborators/complaints/list";
		}	
	}
	
	
	
}
