package com.gdr.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

@Component
public class LogoutSuccessHandler  extends SimpleUrlLogoutSuccessHandler {
 
 
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String redirectURL = request.getContextPath(); 
        redirectURL = "/login";
    	UrlPathHelper helper=new UrlPathHelper();
    	String context=helper.getContextPath(request);
    	response.sendRedirect(context+redirectURL);
              
    }  
}
