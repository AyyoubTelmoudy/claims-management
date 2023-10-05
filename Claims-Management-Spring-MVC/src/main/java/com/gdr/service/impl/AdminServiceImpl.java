package com.gdr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gdr.entities.Admin;
import com.gdr.repositories.AdminRepository;
import com.gdr.services.AdminService;
import com.gdr.dto.PasswordUpdateDto;

@Service
public class AdminServiceImpl implements AdminService {

     @Autowired
     AdminRepository adminRepository;
 	@Autowired
 	BCryptPasswordEncoder bCryptPasswordEncoder;
     
	@Override
	public void updateAdmintPssword(PasswordUpdateDto passwordUpdateDto) {
		Admin admin=adminRepository.findByLogin("kay.technologie.gdr@gmail.com");
		admin.setEncryptedPassword(bCryptPasswordEncoder.encode(passwordUpdateDto.getNewPassword()));
		adminRepository.save(admin);	
	}

}
