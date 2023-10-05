package com.gdr.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdr.entities.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {
 
        UserDetailsImpl userDetails =   (UserDetailsImpl) authentication.getPrincipal();
         
        String redirectURL = request.getContextPath();
         
        if (userDetails.hasRole("ROLE_COLLABORATOR")) {
            redirectURL = "/collaborators/complaints/list";
        } 
        else
        {
        	if(userDetails.hasRole("ROLE_CLIENT"))
        	{
               redirectURL = "/clients/complaints/new";
        	}
        	else 
        	{
        	 if(userDetails.hasRole("ROLE_SUPERVISOR"))
        	 {
          	   redirectURL = "/supervisors/collaborators/complaints/list";
        	 }
        	 else
        	 {
        		 redirectURL = "/supervisors/list";
        	 }
        	}
        }
        response.sendRedirect(redirectURL);  
    }
 
}
