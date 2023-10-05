package com.gdr.controllers;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdr.service.impl.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lowagie.text.DocumentException;
import com.gdr.services.ComplaintService;
import com.itextpdf.text.BadElementException;

@Controller
@RequestMapping("/exports")
public class ReportingController {

	@Autowired
    ComplaintService complaintService;
	@Autowired
    ReportingService exporter;

	
	

    @GetMapping(value="clients/complaints/status/form")
    public String editByStatus()
    {
    	return "complaintsByStatusList";
    }
    @GetMapping(value="/clients/complaints/status")
    public void reclamationsSaisies(HttpServletRequest request,HttpServletResponse response,@RequestParam("statut") String statut) throws DocumentException, IOException, BadElementException, ServletException {  	
    	boolean statusExists=false;
        switch(statut) {
        case "Saisie":
        	statusExists=true;
              break;
          case "En_cours_de_traitement":
          	statusExists=true;
              break;
          case "Traitée_et_Annulée":
          	statusExists=true;
              break;
          case "Toutes":
          	statusExists=true;
              break;
          default :
          	statusExists=false;
     }  	
    	if(statusExists)
    	{
    		Cookie cookie=new Cookie("statut",statut);
    		response.addCookie(cookie);
    		response.sendRedirect("/exports/clients/complaints/status/print");
    	}
    	else
    	{
    		response.sendRedirect("/clients/NotFoundResource");
    	}  
    } 
    @GetMapping(value="/clients/complaints/status/print")
    public void printComplaintBystatus(HttpServletResponse response,HttpServletRequest request) throws DocumentException, IOException, BadElementException {
    	String statut=null;
    	Cookie[] cookies = request.getCookies();
		for ( int i=0; i<cookies.length; i++) {
		      Cookie cookie = cookies[i];
		      if ("statut".equals(cookie.getName()))
		    	  statut=cookie.getValue();
		    }
	        switch(statut) {
	          case "En_cours_de_traitement":
	          	statut="En cours de traitement";
	              break;
	          case "Traitée_et_Annulée":
	        	  statut="Traitée et Annulée";
	              break;
	     }
	    	response.setContentType("application/pdf");
	        exporter.export(response,statut); 
    }
    
   
    @GetMapping(value="/clients/complaints/periode/form")
    public String editByPeriode(Model model)
    {
		model.addAttribute("allComplaints",1);
		model.addAttribute("processedComplaints",2);
    	return "complaintsByPeriodeList";
    }
    @GetMapping(value="/clients/complaints/periode")
    public void editByGivenPeriode(HttpServletResponse response,HttpServletRequest request,@RequestParam("dateDebut") @DateTimeFormat(iso=ISO.DATE) Date dateDebut,@RequestParam("dateFin") @DateTimeFormat(iso=ISO.DATE) Date dateFin) throws DocumentException, IOException, BadElementException {

        	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                	String d1=dateFormatter.format(dateDebut);
                	String d2=dateFormatter.format(dateFin);
                	LocalDate date_debut= LocalDate.parse(d1);
                	LocalDate date_fin= LocalDate.parse(d2);
            		Cookie dateDebutCookie=new Cookie("dateDebut",date_debut.toString());
            		Cookie dateFinCookie=new Cookie("dateFin",date_fin.toString());
            		response.addCookie(dateDebutCookie);
            		response.addCookie(dateFinCookie);
            		response.sendRedirect("/exports/clients/complaints/periode/print");  
      
    }
    
    @GetMapping(value="/clients/complaints/periode/print")
    public void printComplaintsByPeriode(HttpServletResponse response,HttpServletRequest request) throws DocumentException, IOException, BadElementException {
    	String dd="",df="";
    	Cookie[] cookies = request.getCookies();
		for ( int i=0; i<cookies.length; i++) {
		      Cookie cookie = cookies[i];
		      if ("dateDebut".equals(cookie.getName()))
		      { dd=cookie.getValue();}
		      if ("dateFin".equals(cookie.getName()))
		      { df=cookie.getValue();} 
		    }
    	    LocalDate dateDebut=LocalDate.parse(dd);
    	    LocalDate dateFin=LocalDate.parse(df);
        	response.setContentType("application/pdf");
            exporter.export(response,dateDebut,dateFin); 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @GetMapping(value="/clients/complaints/overdue/form")
    public String reclamationsEnsouffrances()
    {
    	return "overdueComplaintsList";
    }
    
    @GetMapping(value="/clients/complaints/overdue")
    public void exportSufferingComplaints(HttpServletResponse response,@RequestParam("nDays")  Integer nDays) throws DocumentException, IOException, BadElementException {
		Cookie days=new Cookie("days",nDays.toString());
		response.addCookie(days);
		response.sendRedirect("/exports/clients/complaints/overdue/print");  
    }
    @GetMapping(value="/clients/complaints/overdue/print")
    public void printOverdueComplaints(HttpServletResponse response,HttpServletRequest request) throws DocumentException, IOException, BadElementException {
    	String days="";
    	Cookie[] cookies = request.getCookies();
		for ( int i=0; i<cookies.length; i++) {
		      Cookie cookie = cookies[i];
		      if ("days".equals(cookie.getName()))
		      { days=cookie.getValue();}
		    }
        response.setContentType("application/pdf");
        exporter.export(response,Integer.parseInt(days)); 
    }
    
    @GetMapping(value="/collaborators/complaints/list")
    public void allComplaints(HttpServletResponse response) throws DocumentException, IOException, BadElementException {
        response.setContentType("application/pdf");
       // response.addHeader("Content-Disposition","attachment; filename=" + "example.pdf");//Download
        //response.addHeader("Content-Disposition","inline; filename=" + "example.pdf"); //View in browser
        exporter.export(response); 
        
    }
    
    
}


