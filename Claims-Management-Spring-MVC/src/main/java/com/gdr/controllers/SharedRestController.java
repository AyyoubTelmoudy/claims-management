package com.gdr.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.gdr.entities.ClientProject;
import com.gdr.service.impl.EmailSenderService;
import com.gdr.service.impl.SharedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdr.entities.Client;
import com.gdr.entities.Supervisor;
import com.gdr.repositories.AdminRepository;
import com.gdr.repositories.ClientProjectRepository;
import com.gdr.repositories.ClientRepository;
import com.gdr.repositories.CollaboratorRepository;
import com.gdr.repositories.SupervisorRepository;
import com.gdr.repositories.UserRepository;
import com.gdr.responses.CheckEmailResponse;
import com.gdr.responses.CheckPasswordResponse;
import com.gdr.security.SecurityConstants;

@RestController
@RequestMapping
public class SharedRestController {
	
	@Autowired
	private EmailSenderService senderService;
	@Autowired
    SharedService sharedService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
    UserRepository userRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
    CollaboratorRepository collaboratorRepository;
	@Autowired
    ClientProjectRepository clientProjectRepository;
	@Autowired
    SupervisorRepository supervisorRepository;
	@Autowired
    AdminRepository adminRepository;
	
    @GetMapping("/mailto")
    public void mailTo(@RequestHeader("ticketNumber") String ticketNumber,@RequestHeader(required = false,name = SecurityConstants.HEADER_STRING) String header) throws IOException
    { 	
    	if(header!=null)
    	{
    		if(header.equals(SecurityConstants.TOKEN_PREFIX+SecurityConstants.TOKEN_SECRET))
    		{
    			Client client=sharedService.getAuthenticatedClient(); 
    		  if(sharedService.getAuthenticatedClient().getStartingEnteringNextComplaintDate()==null)
    		  {
                	List<ClientProject> clientProjects=clientProjectRepository.findByClient(sharedService.getAuthenticatedClient());
                  	for (ClientProject clientProject : clientProjects) 
                  	{
                  		if(clientProject.getCollaborator().getStatus().equals("ACTIVE"))
                  		{
                      		senderService.sendMail(clientProject.getCollaborator().getEmail(),"Nouvelle Réclamation","Un client de la société "+sharedService.getAuthenticatedClient().getSocialReason()+" est entrain de saisir une réclamation.");
                  		}
              		}
                      Iterable<Supervisor> supervisors=supervisorRepository.findAll();
                      for (Supervisor supervisor : supervisors) {
                      	if(supervisor.getStatus().equals("ACTIVE"))
                      	{
                      		senderService.sendMail(supervisor.getEmail(),"Nouvelle Réclamation","Un client de la société "+sharedService.getAuthenticatedClient().getSocialReason()+" est entrain de saisir une réclamation.");
                      	}
              		} 
                   client.setStartingEnteringNextComplaintDate(new Date());
                   clientRepository.save(client);
    		  }
    		  else
    		  {
        			Date oldDate=sharedService.getAuthenticatedClient().getStartingEnteringNextComplaintDate();
          			Date now=new Date();
          			long diffHours=(now.getTime()-oldDate.getTime())/(60 * 60 * 1000) % 24;
          			if(diffHours>=1)
          			{
                      	List<ClientProject> clientProjects=clientProjectRepository.findByClient(sharedService.getAuthenticatedClient());
                      	for (ClientProject clientProject : clientProjects) 
                      	{
                      		if(clientProject.getCollaborator().getStatus().equals("ACTIVE"))
                      		{
                          		senderService.sendMail(clientProject.getCollaborator().getEmail(),"Nouvelle Réclamation","Un client de la société "+sharedService.getAuthenticatedClient().getSocialReason()+" est entrain de saisir une réclamation.");
                      		}
                  		}
                          Iterable<Supervisor> supervisors=supervisorRepository.findAll();
                          for (Supervisor supervisor : supervisors) {
                          	if(supervisor.getStatus().equals("ACTIVE"))
                          	{
                          		senderService.sendMail(supervisor.getEmail(),"Nouvelle Réclamation","Un client de la société "+sharedService.getAuthenticatedClient().getSocialReason()+" est entrain de saisir une réclamation.");
                          	}
                  		}  
                        client.setStartingEnteringNextComplaintDate(now);
                        clientRepository.save(client);
          			}
    		  }
    		}
    		else
    		{
    			throw new RuntimeException("Unauthorized !");
    		}
    	}
    	else
    	{
    		    throw new RuntimeException("Unauthorized !");
    	}
    	//System.out.println("Num = "+ticketNumber);	
    } 
    
    @GetMapping("/clients/check/password")
    public CheckPasswordResponse checkCurrentPassword(@RequestHeader("currentPassword") String currentPassword,@RequestHeader(required = false,name = SecurityConstants.HEADER_STRING) String header) throws IOException
    {
    	if(header!=null)
    	{
    	    if(header.equals(SecurityConstants.TOKEN_PREFIX+SecurityConstants.TOKEN_SECRET))
    	    {
    	    	boolean  isCorrect=false;
    	       isCorrect=bCryptPasswordEncoder.matches(currentPassword,userRepository.findByLogin(sharedService.getAuthenticatedUser().getLogin()).getEncryptedPassword());
    	       if(isCorrect==true)
    	       {   
    	       	return new CheckPasswordResponse("correct");
    	       	
    	       }
    	       else
    	       {
    	       	   return new CheckPasswordResponse("incorrect");
    	       	
    	       }
    	    }
    	    else
    	    {
    	        throw new RuntimeException("Unauthorized !");
    	    }
    	}
    	else
    	{
    	        throw new RuntimeException("Unauthorized !");
    	}
    }  
    
    @GetMapping("/admin/check/password")
    public CheckPasswordResponse checkAdminCurrentPassword(@RequestHeader("currentPassword") String currentPassword,@RequestHeader(required = false,name = SecurityConstants.HEADER_STRING) String header) throws IOException
    {
    	if(header!=null)
    	{
    	    if(header.equals(SecurityConstants.TOKEN_PREFIX+SecurityConstants.TOKEN_SECRET))
    	    {
    	    	boolean  isCorrect=false;
    	       isCorrect=bCryptPasswordEncoder.matches(currentPassword,adminRepository.findByLogin("kay.technologie.gdr@gmail.com").getEncryptedPassword());
    	       if(isCorrect==true)
    	       {   
    	       	return new CheckPasswordResponse("correct");
    	       }
    	       else
    	       {
    	       	   return new CheckPasswordResponse("incorrect");
    	       }
    	    }
    	    else
    	    {
    	        throw new RuntimeException("Unauthorized !");
    	    }
    	}
    	else
    	{
    	        throw new RuntimeException("Unauthorized !");
    	}
    }  
    
     @GetMapping("/clients/check/email")
    public CheckEmailResponse existClientEmail(@RequestHeader("clientEmail") String clientEmail,@RequestHeader(required = false,name = SecurityConstants.HEADER_STRING) String header) throws IOException
    {
    	 if(header!=null)
    	 {
    	     if(header.equals(SecurityConstants.TOKEN_PREFIX+SecurityConstants.TOKEN_SECRET))
    	     {
    	         if("kay.technologie.gdr@gmail.com".equals(clientEmail)||clientRepository.findByEmail(clientEmail)!=null||collaboratorRepository.findByEmail(clientEmail)!=null||supervisorRepository.findByEmail(clientEmail)!=null)
    	         {  
    	             return new CheckEmailResponse("true");
    	         }
    	         else
    	         {
    	                return new CheckEmailResponse("false");
    	         }
    	     }
    	     else
    	     {
    	         throw new RuntimeException("Unauthorized !");
    	     }
    	 }
    	 else
    	 {
    	         throw new RuntimeException("Unauthorized !");
    	 }
    }  
     
     @GetMapping("/supervisors/check/email")
    public CheckEmailResponse existSupervisorEmail(@RequestHeader("supervisorEmail") String supervisorEmail,@RequestHeader(required = false,name = SecurityConstants.HEADER_STRING) String header) throws IOException
    {
    	 if(header!=null)
    	 {
    	     if(header.equals(SecurityConstants.TOKEN_PREFIX+SecurityConstants.TOKEN_SECRET))
    	     {
    	         if("kay.technologie.gdr@gmail.com".equals(supervisorEmail)||clientRepository.findByEmail(supervisorEmail)!=null||collaboratorRepository.findByEmail(supervisorEmail)!=null||supervisorRepository.findByEmail(supervisorEmail)!=null)
    	         {  
    	             return new CheckEmailResponse("true");
    	         }
    	         else
    	         {
    	                return new CheckEmailResponse("false");
    	         }
    	     }
    	     else
    	     {
    	         throw new RuntimeException("Unauthorized !");
    	     }
    	 }
    	 else
    	 {
    	         throw new RuntimeException("Unauthorized !");
    	 }
    } 
     
     @GetMapping("/collaborators/check/password")
     public CheckPasswordResponse checkCollaboratorCurrentPassword(@RequestHeader("currentPassword") String currentPassword,@RequestHeader(required = false,name = SecurityConstants.HEADER_STRING) String header) throws IOException
     {
     	if(header!=null)
     	{
     	    if(header.equals(SecurityConstants.TOKEN_PREFIX+SecurityConstants.TOKEN_SECRET))
     	    {
     	    	boolean  isCorrect=false;
     	       isCorrect=bCryptPasswordEncoder.matches(currentPassword,userRepository.findByLogin(sharedService.getAuthenticatedUser().getLogin()).getEncryptedPassword());
     	       if(isCorrect==true)
     	       {  
     	       	return new CheckPasswordResponse("correct");
     	       }
     	       else
     	       {
     	       	   return new CheckPasswordResponse("incorrect");
     	       }
     	    }
     	    else
     	    {
     	        throw new RuntimeException("Unauthorized !");
     	    }
     	}
     	else
     	{
     	        throw new RuntimeException("Unauthorized !");
     	}
     } 
     
     
    @GetMapping("/collaborators/check/email")
    public CheckEmailResponse existCollaboratorEmail(@RequestHeader("collaboratorEmail") String collaboratorEmail,@RequestHeader(required = false,name = SecurityConstants.HEADER_STRING) String header) throws IOException
    {
    	if(header!=null)
    	{
    	    if(header.equals(SecurityConstants.TOKEN_PREFIX+SecurityConstants.TOKEN_SECRET))
    	    {
    	        if("kay.technologie.gdr@gmail.com".equals(collaboratorEmail)||clientRepository.findByEmail(collaboratorEmail)!=null||collaboratorRepository.findByEmail(collaboratorEmail)!=null||supervisorRepository.findByEmail(collaboratorEmail)!=null)
    	       { 
    	       	return new CheckEmailResponse("true");
    	       }
    	       else
    	       {
    	       	   return new CheckEmailResponse("false");
    	       }
    	    }
    	    else
    	    {
    	        throw new RuntimeException("Unauthorized !");
    	    }
    	}
    	else
    	{
    	        throw new RuntimeException("Unauthorized !");
    	}
    } 
    
    @GetMapping("/supervisors/check/password")
    public CheckPasswordResponse checkSupervisorCurrentPassword(@RequestHeader("currentPassword") String currentPassword,@RequestHeader(required = false,name = SecurityConstants.HEADER_STRING) String header) throws IOException
    {
    	if(header!=null)
    	{
    	    if(header.equals(SecurityConstants.TOKEN_PREFIX+SecurityConstants.TOKEN_SECRET))
    	    {
    	    	boolean  isCorrect=false;
    	       isCorrect=bCryptPasswordEncoder.matches(currentPassword,sharedService.getAuthenticatedSupervisor().getEncryptedPassword());
    	       if(isCorrect==true)
    	       {   
    	       	return new CheckPasswordResponse("correct");
    	       }
    	       else
    	       {
    	       	   return new CheckPasswordResponse("incorrect");
    	       }
    	    }
    	    else
    	    {
    	        throw new RuntimeException("Unauthorized !");
    	    }
    	}
    	else
    	{
    	        throw new RuntimeException("Unauthorized !");
    	}
    } 
}








