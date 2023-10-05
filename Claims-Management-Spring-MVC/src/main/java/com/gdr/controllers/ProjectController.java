package com.gdr.controllers;

import java.util.List;

import com.gdr.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdr.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	//Get all projects
	@GetMapping("/list")
	public String getAllProjects(Model model) {	
		List<ProjectDto> projectsDto=projectService.getAllProjects();
		model.addAttribute("projectsDto",projectsDto);
		return "projectsList";
	}
	//Create a new project
	@GetMapping("/new")
	public String createProject(Model model) {
		model.addAttribute("projectDto",new ProjectDto());
		return "createProject";
	}
	//Save a new project
	@PostMapping("/save")
	public String saveProject(@ModelAttribute("projectDto") ProjectDto projectDto) {
		projectService.saveProject(projectDto);	
		return "redirect:/projects/list";
	}
	
	//Edit project
	@GetMapping("/edit/{publicId}")
	public String editProject(Model model,@PathVariable String publicId) {	
		ProjectDto projectDto=projectService.getProject(publicId);
		if(projectDto==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			model.addAttribute("projectDto",projectDto);
			return "editProject";
		}
	}
	
	//Update project
	@PostMapping("/update")
	public String updateProject(@ModelAttribute("projectDto") ProjectDto projectDto) {
		projectService.updateProject(projectDto);
		return "redirect:/projects/list";
	}
	
	//Delete project
	@GetMapping("/delete/{publicId}")
	public String deleteProject(@PathVariable String publicId) {
		if(projectService.deleteProject(publicId)==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			return "redirect:/projects/list";
		}
			
	}
	//Delete project
	@GetMapping("/details/{publicId}")
	public String projectDetails(@PathVariable String publicId,Model model) {
		ProjectDto projectDto=projectService.getProject(publicId);
		if(projectDto==null)
		{
			return "supervisorNotFoundResource";
		}
		else
		{
			model.addAttribute("projectDto",projectDto);
			return "projectDetails";
		}	
	}

}

