package com.gdr;

import com.gdr.constants.ResourcesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gdr.entities.Admin;
import com.gdr.repositories.AdminRepository;


@SpringBootApplication
public class ClaimsManagementApp implements CommandLineRunner{
    @Autowired
	AdminRepository adminRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ClaimsManagementApp.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		if(adminRepository.findByLogin("kay.technologie.gdr@gmail.com")==null)
		{
			this.adminRepository.save(new Admin("kay.technologie.gdr@gmail.com",bCryptPasswordEncoder.encode(ResourcesConstants.DEFAULT_PASSWORD),"kay.technologie.gdr@gmail.com"));
		}
			
	}
	 @Bean
	 public  BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	 @Bean
	 public SpringApplicationContext springApplicationContext()
	 {
		 return new SpringApplicationContext();
	 }
	 
}
