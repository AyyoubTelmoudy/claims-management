package com.gdr.controllers;

import java.util.List;

import com.gdr.dto.ComplaintTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdr.services.ComplaintTypeService;
;
@Controller
@RequestMapping("/complainttypes")
public class ComplaintTypeController {
	
	@Autowired
	ComplaintTypeService complaintTypeService;
	
	//Get all projects
	@GetMapping("/list")
	public String getAllTypes(Model model) {	
		List<ComplaintTypeDto> complaintTypesDto=complaintTypeService.getAllTypes();
		model.addAttribute("complaintTypesDto",complaintTypesDto);
		return "complaintTypesList";
	}
	//Create a new project
	@GetMapping("/new")
	public String createComplaintType(Model model) {
		model.addAttribute("complaintTypeDto",new ComplaintTypeDto());
		return "createComplaintType";
	}
	//Save a new project
	@PostMapping("/save")
	public String saveComplaintType(@ModelAttribute("complaintTypeDto") ComplaintTypeDto complaintTypeDto) {
		complaintTypeService.saveComplaintType(complaintTypeDto);	
		return "redirect:/complainttypes/list";
	}
	
	//Edit project
	@GetMapping("/edit/{publicId}")
	public String editComplaintType(Model model,@PathVariable String publicId) {
		
		ComplaintTypeDto complaintTypeDto=complaintTypeService.getComplaintType(publicId);
		if(complaintTypeDto==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			model.addAttribute("complaintTypeDto",complaintTypeDto);
			return "editComplaintType";	
		}

	}
	
	//Update project
	@PostMapping("/update")
	public String updateComplaintType(@ModelAttribute("complaintTypeDto") ComplaintTypeDto complaintTypeDto) {
		complaintTypeService.updateComplaintType(complaintTypeDto);
		return "redirect:/complainttypes/list";
	}
	
	
	//Delete project
	@GetMapping("/delete/{publicId}")
	public String deleteComplaintType(@PathVariable String publicId) {
		if(complaintTypeService.deleteComplaintType(publicId)==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			return "redirect:/complainttypes/list";
		}
	}


}
