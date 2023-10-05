package com.gdr.controllers;

import com.gdr.entities.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SharedController {

	
    @GetMapping("/login")
    public String login(@RequestParam(name ="error",required = false) String error,Model model)
    {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
        {
            if (((UserDetailsImpl) principal).hasRole("COLLABORATOR")) {
                return "redirect:/collaborators/complaints/list";
            } 
            else
            {
            	if(((UserDetailsImpl) principal).hasRole("CLIENT"))
            	{
            		return "redirect:/clients/complaints/new";
            	}
            	else 
            	{
            		if(((UserDetailsImpl) principal).hasRole("SUPERVISOR"))
            		{
            			return "redirect:/supervisors/collaborators/complaints/list";
            		}
            		else
            		{
            			return "redirect:/supervisors/list";
            		}
            		
            	}
            }
        }
        boolean wrongCredentials=false;
    	if(error!=null)
    	{
    		wrongCredentials=true;
    	}
    	model.addAttribute("wrongCredentials",wrongCredentials);
        return "login";    
    }
    @GetMapping("/")
    public String home()
    {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
        {
            if (((UserDetailsImpl) principal).hasRole("COLLABORATOR")) {
                return "redirect:/collaborators/complaints/list";
            } 
            else
            {
            	if(((UserDetailsImpl) principal).hasRole("CLIENT"))
            	{
            		return "redirect:/clients/complaints/new";
            	}
            	else 
            	{
            		if(((UserDetailsImpl) principal).hasRole("SUPERVISOR"))
            		{
            			return "redirect:/supervisors/collaborators/complaints/list";
            		}
            		else
            		{
            			return "redirect:/supervisors/list";
            		}
            		
            	}
            }
        }
    	return "index";
    }
    
    
    @GetMapping("/clients/NotFoundResource")
    public String clientNotFoundResource()
    {
    	return "clientNotFoundResource";
    }
    @GetMapping("/supervisors/NotFoundResource")
    public String supervisorNotFoundResource()
    {
    	return "supervisorNotFoundResource";
    }


    
    
    
}
