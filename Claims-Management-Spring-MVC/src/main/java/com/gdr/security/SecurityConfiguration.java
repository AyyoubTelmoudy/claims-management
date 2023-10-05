package com.gdr.security;

import com.gdr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final UserService serviceUtilisateur;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public SecurityConfiguration(UserService serviceUtilisateur, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.serviceUtilisateur = serviceUtilisateur;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(serviceUtilisateur).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
      .antMatchers("/","/static/**").permitAll()
      
      
      .antMatchers(
    		  "/clients/password/update/form",
    		  "/clients/password/update",
    		  "/clients/projects/list",
    		  "/clients/complaints/responses",
    		  "/clients/complaints/convesations/**",
    		  "/clients/complaints/conversations/**/messages",
    		  "/clients/complaints/conversations/**/messages/image",
    		  "/clients/complaints/list",
    		  "/clients/complaints/new",
    		  "/clients/complaints",
    		  "/clients/complaints/details/**",
    		  "/exportsclients/complaints/status/form",
    		  "/exports/clients/complaints/status",
    		  "/exports/clients/complaints/periode/form",
    		  "/exports/clients/complaints/periode",
    		  "/exports/clients/complaints/overdue/form",
    		  "/exports/clients/complaints/overdue",
    		  "/clients/NotFoundResource"    		  
    		  ).hasAnyRole("CLIENT")
      .antMatchers(
    		  "/collaborators/password/update",
    		  "/collaborators/password/update/form",
    		  "/collaborators/password/update",
    		  "/collaborators/complaints/periode/form",
    		  "/collaborators/complaints/periode",
    		  "/collaborators/projects/list",
    		  "/collaborators/complaints/**/conversations",
    		  "/collaborators/complaints/**/conversations/messages",
    		  "/collaborators/complaints/**/conversations/messages/image",
    		  "/collaborators/complaints/list",
    		  "/collaborators/complaints/**/close"
    		  
    		  ).hasAnyRole("COLLABORATOR")
      .antMatchers(
    		  "/clients/list",
    		  "/clients/new",
    		  "/clients/save",
    		  "/clients/edit/**",
    		  "/clients/edit",
    		  "/clients/update",
    		  "/clients/delete/**",
    		  "/clients/delete",
    		  "/clients/block/**",
    		  "/clients/block",
    		  
    		  "/collaborators/list",
    		  "/collaborators/new",
    		  "/collaborators/save",
    		  "/collaborators/edit/**",
    		  "/collaborators/edit",
    		  "/collaborators/update",
    		  "/collaborators/delete/**",
    		  "/collaborators/delete",
    		  
    		  "/complainttypes/list",
    		  "/complainttypes/new",
    		  "/complainttypes/save",
    		  "/complainttypes/edit/**",
    		  "/complainttypes/edit",
    		  "/complainttypes/update",
    		  "/complainttypes/delete/**",
    		  "/complainttypes/delete",

       		  "/projects/list",
    		  "/projects/new",
    		  "/projects/save",
    		  "/projects/edit/**",
    		  "/projects/edit",
    		  "/projects/update",
    		  "/projects/delete/**",
    		  "/projects/delete",
    		  "/projects/edit/**",
    		  "/projects/edit",
    		  "/projects/details/**",
    		  "/projects/details",
    		  
    		  "/exports/collaborators/complaints/list",
    		  		  
    		  "/supervisors/collaborators/complaints/list",
    		  "/supervisors/projects/assign/form",
    		  "/supervisors/projects/assign",
    		  "/supervisors/collaborators/complaints/details/**",
    		  "/supervisors/collaborators/complaints/details",
    		  "/supervisors/password/update/form",
    		  "/supervisors/password/update",
    		  "/supervisors/NotFoundResource"		  
    		  ).hasAnyRole("SUPERVISOR")
      
      .antMatchers(
    		  "/admin/**",
    		  "/admin",
    		  
       		  "/supervisors/list",
    		  "/supervisors/new",
    		  "/supervisors/save",
    		  "/supervisors/edit/**",
    		  "/supervisors/edit",
    		  "/supervisors/update",
    		  "/supervisors/delete/**",
    		  "/supervisors/delete",
    		  "/supervisors/edit/**",
    		  "/supervisors/edit",
    		  "/supervisors/block/**",
    		  "/supervisors/block"
    		  ).hasAnyRole("ADMIN")
      
      .anyRequest().authenticated()
      .and()
      .formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password")
      .permitAll().successHandler(loginSuccessHandler)
      .and()
      .logout().logoutSuccessHandler(logoutSuccessHandler).permitAll(); 
	}
	
	@Autowired 
	private LoginSuccessHandler loginSuccessHandler;
	
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler; 

}
