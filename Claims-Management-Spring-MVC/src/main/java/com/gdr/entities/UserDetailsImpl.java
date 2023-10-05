package com.gdr.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
public class UserDetailsImpl implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2613661939383105307L;

	private User utilisateur;
	private Supervisor superviseur;
	private Admin admin;
	
	
    public UserDetailsImpl(User utilisateur) {
		super();
		this.utilisateur = utilisateur;
	}

    
    public UserDetailsImpl(Supervisor superviseur) {
    	super();
    	this.superviseur = superviseur;
	}

    public UserDetailsImpl(Admin admin) {
    	super();
    	this.admin =admin;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    	if(this.utilisateur!=null)
    	{
    		authorities.add(new SimpleGrantedAuthority(utilisateur.getRole()));
            return authorities;
    	}
    	else
    	{
    	   if(this.superviseur!=null)
    	   {
       		authorities.add(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
       		return authorities;
    	   }
    	   else
    	   {
          		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
           		return authorities;
    	   }
    	}
    }	
    @Override
    public String getPassword() { 
    	//BCryptPasswordEncoder bCryptPasswordEncoder=(BCryptPasswordEncoder) SpringApplicationContext.getBean("bCryptPasswordEncoder");   	
    	if(this.utilisateur!=null)
    	{
    		return utilisateur.getEncryptedPassword();
    	}
    	else
    	{
    	   if(this.superviseur!=null)
    	   {
    		   return superviseur.getEncryptedPassword();
    	   }
    	   else
    	   {
    		   return admin.getEncryptedPassword();
    	   }
    	}   	
    }
 
    @Override
    public String getUsername() {
    	if(this.utilisateur!=null)
    	{
    		return utilisateur.getLogin();
    	}
    	else
    	{
    	   if(this.superviseur!=null)
    	   {
    		   return superviseur.getLogin();
    	   }
    	   else
    	   {
    		   return admin.getLogin();
    	   }
    	}  
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {     
    	if(this.utilisateur!=null)
    	{
    		if(utilisateur.getRole().equals("ROLE_COLLABORATOR"))
    		{
    	 		   if(utilisateur.getStatus().equals("ACTIVE"))
    				   return true;
    			   else
    				   return false;
    		}
    		else
    		{
    			return true;
    		}
    	}
    	else
    	{
    	   if(this.superviseur!=null)
    	   {
    		   if(superviseur.getStatus().equals("ACTIVE"))
    			   return true;
    		   else
    			   return false;
    	   }
    	   else
    	   {
    		   return true;
    	   }
    	}  
    }
    public boolean hasRole(String roleName)
    { 	
    	if(utilisateur!=null)
    	{
    		return utilisateur.hasRole(roleName);
    	}
    	else
    	{
        	if(superviseur!=null)
        	{
        		if(roleName.equals("ROLE_SUPERVISOR"))
        			return true;
        		else
        			return false;
        	}
        	else
        	{
        		if(roleName.equals("ROLE_ADMIN"))
        			return true;
        		else
        			return false;
        	}
    	}
    }

}
