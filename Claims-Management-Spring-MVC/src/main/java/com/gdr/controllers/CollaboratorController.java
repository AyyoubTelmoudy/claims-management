package com.gdr.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdr.service.impl.SharedService;
import com.gdr.shared.Utils;
import com.gdr.dto.CollaboratorDto;
import com.gdr.dto.ComplaintDto;
import com.gdr.dto.PasswordUpdateDto;
import com.gdr.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdr.services.CollaboratorService;
import com.gdr.services.ComplaintService;
import com.gdr.services.ProjectService;
import com.itextpdf.text.BadElementException;
import com.lowagie.text.DocumentException;


@Controller
@RequestMapping("/collaborators")
public class CollaboratorController {
     
	@Autowired
	CollaboratorService collaboratorService;
	@Autowired
	ProjectService projectService;
	@Autowired
	ComplaintService complaintService;
	
	@Autowired
    SharedService sharedService;
	private String randomString="pjolpmlphlptogkrofi3ehhusn28sj9isowksoqwsqoso";
	private String randomString_2="7yIUYiuajqnasuioq238239ekm7897wsuhsuwhus7UiouIOUio";
	private String randomString_3="jiahiudhxiojxio23jeo93uie903i3jiosjwsjwijkjjiows";
	//Get all projects
	@GetMapping("/list")
	public String getAllCollaborators(Model model) {	
		List<CollaboratorDto> collaboratorsDto=collaboratorService.getAllCollaborators();
		model.addAttribute("collaboratorsDto",collaboratorsDto);
		return "collaboratorsList";
	}
	
	//Create a new project
	@GetMapping("/new")
	public String createCollaborator(Model model) {
		CollaboratorDto collaboratorDto=new CollaboratorDto();
		collaboratorDto.setStatus("BLOCKED");
		model.addAttribute("collaboratorDto",collaboratorDto);
		return "createCollaborator";
	}
	//Save a new project
	@PostMapping("/save")
	public String saveCollaborator(@ModelAttribute("collaboratorDto") CollaboratorDto collaboratorDto) {
		collaboratorService.saveCollaborator(collaboratorDto);	
		return "redirect:/collaborators/list";
	}
	
	//Edit project
	@GetMapping("/edit/{publicId}")
	public String editCollaborator(Model model,@PathVariable String publicId) {	
		CollaboratorDto collaboratorDto=collaboratorService.getCollaborator(publicId);
		if(collaboratorDto==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			model.addAttribute("collaboratorDto",collaboratorDto);
			return "editCollaborator";
		}	
	}
	@PostMapping("/update")
	public String updateCollaborator(@ModelAttribute("collaboratorDto") CollaboratorDto collaboratorDto) {
		collaboratorService.updateCollaborator(collaboratorDto);
		return "redirect:/collaborators/list";
	}
	@GetMapping("/delete/{publicId}")
	public String deleteCollaborator(@PathVariable String publicId) {
		CollaboratorDto collaboratorDto=collaboratorService.deleteCollaborator(publicId);
		if(collaboratorDto==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			return "redirect:/collaborators/list";
		}		
	}
	@GetMapping("/projects/list")
	public String getCollaboratorProjects(Model model) {
		List<ProjectDto> projectsDto=projectService.getCollaboratorProjects(sharedService.getAuthenticatedCollaborator());
		model.addAttribute("projectsDto",projectsDto);
		return "collaboratorProjects";		
	}
	@GetMapping("/password/update/form")
	public String updateCollaboratorPsswordForm(HttpServletRequest request,Model model) {
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
    	return "collaboratorPasswordUpdateForm";
	} 
	@PostMapping("/password/update")
	public String updateCollaboratorPssword(HttpServletResponse response,@ModelAttribute("passwordUpdateDto") PasswordUpdateDto passwordUpdateDto) throws IOException {	
		collaboratorService.updateCollaboratorPassword(passwordUpdateDto);
		randomString=Utils.genereteRandomString(30);
		Cookie cookie=new Cookie(randomString,"updated");
		response.addCookie(cookie);
	    return "redirect:/collaborators/password/update/form";
	}
	
	@GetMapping("/block/{publicId}")
	public String blockCollaborator(@PathVariable String publicId) {
		CollaboratorDto collaboratorDto=collaboratorService.blockCollaborator(collaboratorService.getCollaborator(publicId));
		if(collaboratorDto==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			return "redirect:/collaborators/list";
		}	
	}

    @GetMapping(value="/complaints/periode/form")
    public String consultByPeriode(Model model,HttpServletRequest request)
    {
		boolean existsCookies=false;
		Cookie[] cookies = request.getCookies();
		String cookieName_1= randomString_2;
		String cookieName_2= randomString_3;
		
		for ( int i=0; i<cookies.length; i++) 
		{
		      Cookie cookie = cookies[i];
		      if (cookieName_1.equals(cookie.getName()))
		    	  {
		    	    existsCookies=true;
		    	    model.addAttribute("allComplaints",cookie.getValue());
		    	    randomString_2=Utils.genereteRandomString(30);
		    	  }
		      if (cookieName_2.equals(cookie.getName()))
		    	  {
		    	    existsCookies=true;
		    	    model.addAttribute("processedComplaints",cookie.getValue());
		    	    randomString_3=Utils.genereteRandomString(30);
		    	  }
		}
		if(!existsCookies)
		{
			model.addAttribute("allComplaints","");
			model.addAttribute("processedComplaints","");
		}
		
    	return "CollaboratorComplaintsByPeriod";
    }
	
    @GetMapping(value="/complaints/periode")
    public void consultByGivenPeriode(HttpServletResponse response,HttpServletRequest request,@RequestParam("dateDebut") @DateTimeFormat(iso=ISO.DATE) Date dateDebut,@RequestParam("dateFin") @DateTimeFormat(iso=ISO.DATE) Date dateFin) throws DocumentException, IOException, BadElementException {

        	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                	String d1=dateFormatter.format(dateDebut);
                	String d2=dateFormatter.format(dateFin);
                	LocalDate date_debut= LocalDate.parse(d1);
                	LocalDate date_fin= LocalDate.parse(d2);
                    List<ComplaintDto> complaintsDto=complaintService.getCollaboratorComplaints();
                    for(int i=0;i<complaintsDto.size();i++)
                    {   
                    	if(complaintsDto.get(i).getComplaintDate().isBefore(date_debut)||complaintsDto.get(i).getComplaintDate().isAfter(date_fin))
                    	{
                    		complaintsDto.remove(i);	
                    		i--;
                    	}
                    }
                    int processedComp=0;
                    for (ComplaintDto complaintDto:complaintsDto) {
						if(complaintDto.getStatus().equals("Traitée et Annulée"))
						{
							processedComp++;	
						}
					}
                    
            		Cookie allComplaints=new Cookie(randomString_2,complaintsDto.size()+"");
            		Cookie processedComplaints=new Cookie(randomString_3,processedComp+"");
            		response.addCookie(allComplaints);
            		response.addCookie(processedComplaints);
            		response.sendRedirect("/collaborators/complaints/periode/form");  
    }
    
	
}
