package com.gdr.controllers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdr.shared.Utils;
import com.gdr.dto.PasswordUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gdr.services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	private String randomString="7yIUYiuy9789oiUIOU8ou7897UiouIOUio";

	@GetMapping("/password/update/form")
	public String updateAdminPsswordForm(HttpServletRequest request,Model model) {
		boolean existsCookies=false;
		Cookie[] cookies = request.getCookies();
		String cookieName = randomString;
		for ( int i=0; i<cookies.length; i++) {
		      Cookie cookie = cookies[i];
		      if (cookieName.equals(cookie.getName()))
		    	  existsCookies=true;
		    }
		if(existsCookies)
		{
			model.addAttribute("passwordUpdated","true");
			randomString= Utils.genereteRandomString(30);
		}
		else
		{
			model.addAttribute("passwordUpdated","false");
			model.addAttribute("passwordUpdateDto",new PasswordUpdateDto());
		}
    	return "adminPasswordUpdateForm";
	} 
	@PostMapping("/password/update")
	public String updateAdminPssword(HttpServletResponse response,@ModelAttribute("passwordUpdateDto") PasswordUpdateDto passwordUpdateDto) throws IOException {	
		adminService.updateAdmintPssword(passwordUpdateDto);
		randomString=Utils.genereteRandomString(30);
		Cookie cookie=new Cookie(randomString,"updated");
		response.addCookie(cookie);
	    return "redirect:/admin/password/update/form";
	}
	
	

}
