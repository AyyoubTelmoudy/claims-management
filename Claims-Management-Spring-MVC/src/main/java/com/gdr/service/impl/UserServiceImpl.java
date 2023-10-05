package com.gdr.service.impl;
import com.gdr.entities.User;
import com.gdr.entities.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gdr.entities.Admin;
import com.gdr.entities.Supervisor;
import com.gdr.repositories.AdminRepository;
import com.gdr.repositories.SupervisorRepository;
import com.gdr.repositories.UserRepository;
import com.gdr.services.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	SupervisorRepository supervisorRepository;
	@Autowired
	AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user=userRepository.findByLogin(login.trim());
		if(user==null)
		{
		  Supervisor superviseur=supervisorRepository.findByLogin(login);
		  if(superviseur==null)
		  {
			  Admin admin=adminRepository.findByLogin(login);
			  if(admin==null)
			  {
				  throw new UsernameNotFoundException(login);  
			  }
			  else
			  {
				  return new UserDetailsImpl(admin);
			  } 
		  }
		  else
		  {
			  return new UserDetailsImpl(superviseur);
		  }
		}
		else
		{
			return new UserDetailsImpl(user);	
		}
	}


	
}
