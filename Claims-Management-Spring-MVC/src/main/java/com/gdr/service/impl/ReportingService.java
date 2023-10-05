package com.gdr.service.impl;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.gdr.entities.Attachment;
import com.itextpdf.text.BadElementException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gdr.entities.Complaint;
import com.gdr.repositories.AttachmentRepository;
import com.gdr.repositories.ClientRepository;
import com.gdr.repositories.ComplaintRepository;
import com.gdr.repositories.UserRepository;
import com.gdr.services.ComplaintService;
import com.gdr.dto.ComplaintDto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

 @Service
public class ReportingService {

    @Autowired
    AttachmentRepository  attachmentRepository;
	@Autowired
	SharedService sharedService;
	@Autowired
    ComplaintService complaintService;
    @Autowired
    ComplaintRepository  complaintRepository;
    @Autowired
    UserRepository  userRepository;
    @Autowired
    ClientRepository  clientRepository;
    
    public ReportingService() {  
    }
    
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);
         
        cell.setPhrase(new Phrase("Numéro de réclamation ", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Client ", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Email de collaborateur ", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Type ", font));
        table.addCell(cell);  
        
        cell.setPhrase(new Phrase("Date ", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Description ", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Etat ", font));
        table.addCell(cell);   
         
    }  
    private void writeTableData(PdfPTable table,Complaint reclamation) {    	
                Font font1= FontFactory.getFont(FontFactory.HELVETICA);
                font1.setColor(Color.GRAY);
                PdfPCell cell1 = new PdfPCell();
                cell1.setPhrase(new Phrase(String.valueOf(reclamation.getTicketNumber()),font1)); 
                cell1.setBackgroundColor(Color.WHITE);
                table.addCell(cell1); 
                
                Font font6= FontFactory.getFont(FontFactory.HELVETICA);
                font6.setColor(Color.GRAY);
                PdfPCell cell6 = new PdfPCell();
                cell6.setPhrase(new Phrase(String.valueOf(reclamation.getClientProject().getClient().getSocialReason()),font6)); 
                cell6.setBackgroundColor(Color.WHITE);
                table.addCell(cell6); 
                
                Font font7= FontFactory.getFont(FontFactory.HELVETICA);
                font7.setColor(Color.GRAY);
                PdfPCell cell7 = new PdfPCell();
                cell7.setPhrase(new Phrase(String.valueOf(reclamation.getClientProject().getCollaborator().getEmail())+"\n",font7)); 
                cell7.setBackgroundColor(Color.WHITE);
                table.addCell(cell7); 
                
                Font font2= FontFactory.getFont(FontFactory.HELVETICA);
                font2.setColor(Color.GRAY);
                PdfPCell cell2 = new PdfPCell();
                cell2.setPhrase(new Phrase(reclamation.getComplaintType().getName(),font2));
                cell2.setBackgroundColor(Color.WHITE);
                table.addCell(cell2);

                Font font3= FontFactory.getFont(FontFactory.HELVETICA);
                font3.setColor(Color.GRAY);
                PdfPCell cell3 = new PdfPCell();
                cell3.setPhrase(new Phrase(String.valueOf( reclamation.getComplaintDate()),font3));   
                cell3.setBackgroundColor(Color.WHITE);
                table.addCell(cell3);
                
                Font font4= FontFactory.getFont(FontFactory.HELVETICA);
                font4.setColor(Color.GRAY);
                PdfPCell cell4 = new PdfPCell();
                cell4.setPhrase(new Phrase(String.valueOf( reclamation.getDescription())+"\n",font4));   
                cell4.setBackgroundColor(Color.WHITE);
                table.addCell(cell4);
                
                Font font5= FontFactory.getFont(FontFactory.HELVETICA);
                font5.setColor(Color.GRAY);
                PdfPCell cell5 = new PdfPCell();
                cell5.setPhrase(new Phrase(String.valueOf(reclamation.getStatus())+"\n",font5)); 
                cell5.setBackgroundColor(Color.WHITE);
                table.addCell(cell5);
            }
    public void export(HttpServletResponse response) throws DocumentException, IOException, BadElementException {  
        List<ComplaintDto> complaintsDto=complaintService.getAllComplaints();
     
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p1;
        p1 = new Paragraph("List des réclamations reçues de tous les client", font);
        p1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p1);
        
        for (ComplaintDto complaintDto : complaintsDto) 
        { 
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100f);
            table.setWidths(new float[] {1.5f,2f,3f,2f,1.5f,2f,1.5f});
            table.setSpacingBefore(10);
            writeTableHeader(table);
            writeTableData(table,complaintRepository.findByPublicId(complaintDto.getPublicId()));  
            document.add(table); 
            p1 = new Paragraph("Capture d’écran : ", font);
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(p1);
            
            List<Attachment>  attachments=attachmentRepository.findByComplaint(complaintRepository.findByPublicId(complaintDto.getPublicId()));
            for (Attachment attachment : attachments) 
            {
                Paragraph nomPiece = new Paragraph(attachment.getAttachmentName(),font);
                nomPiece .setAlignment(Paragraph.ALIGN_CENTER);
                document.add(nomPiece);
                String pathPiece=attachment.getPath();
                pathPiece=pathPiece.replace("/upload/","");
                Image image =Image.getInstance(FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"\\src\\main\\resources\\upload\\"+pathPiece);
                //image.setAbsolutePosition(100f,200f);
                image.scaleAbsolute(520f,600f);
                document.add(image);
                document.newPage();
            }
           document.newPage();
        }
        
        document.close();      
    } 
 
    
    
     //Vesion 1
    public void export(HttpServletResponse response,LocalDate dateDebut,LocalDate dateFin) throws DocumentException, IOException, BadElementException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        List<ComplaintDto> complaintsDto=complaintService.clientComplaints(clientRepository.findByUser(sharedService.getAuthenticatedUser()));
        for(int i=0;i<complaintsDto.size();i++)
        {   
        	if(complaintsDto.get(i).getComplaintDate().compareTo(dateDebut)<0||complaintsDto.get(i).getComplaintDate().compareTo(dateFin)>0)
        	{
        		complaintsDto.remove(i);	
        		i--;
        	}
        }
        if(complaintsDto.size()<1)
        {
            Paragraph p1 = new Paragraph("Il n'y a pas de réclamation établie pendant la période de "+dateDebut+" à "+dateFin,font);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p1);
            document.close(); 
         }
        else
        {
            Paragraph p1 = new Paragraph("Les réclamations établies pendant la période de "+dateDebut+" à "+dateFin,font);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p1);
            for (ComplaintDto complaintDto : complaintsDto) 
            { 
                PdfPTable table = new PdfPTable(7);
                table.setWidthPercentage(100f);
                table.setWidths(new float[] {1.5f,2f,3f,2f,1.5f,2f,1.5f});
                table.setSpacingBefore(10);
                writeTableHeader(table);
                writeTableData(table,complaintRepository.findByPublicId(complaintDto.getPublicId()));  
                document.add(table);  
                p1 = new Paragraph("Capture d’écran : ", font);
                p1.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(p1);
                List<Attachment>  attachments=attachmentRepository.findByComplaint(complaintRepository.findByPublicId(complaintDto.getPublicId()));  
                for (Attachment attachment : attachments) 
                {
                    Paragraph nomPiece = new Paragraph(attachment.getAttachmentName(),font);
                    nomPiece .setAlignment(Paragraph.ALIGN_CENTER);
                    document.add(nomPiece);
                    String pathPiece=attachment.getPath();
                    pathPiece=pathPiece.replace("/upload/","");
                    Image image =Image.getInstance(FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"\\src\\main\\resources\\upload\\"+pathPiece);
                    //image.setAbsolutePosition(100f,200f);
                    image.scaleAbsolute(520f,600f);
                    document.add(image);
                    document.newPage();
                }
                document.newPage(); 
            }
            document.close(); 
        }

    
    }
    //Vesion 2
   public void export(HttpServletResponse response,int nDays) throws DocumentException, IOException, BadElementException {
       Document document = new Document(PageSize.A4);
       PdfWriter.getInstance(document, response.getOutputStream());
       document.open();
       Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
       font.setSize(18);
       font.setColor(Color.BLACK);
       List<ComplaintDto> complaintsDto=complaintService.clientComplaints(clientRepository.findByUser(sharedService.getAuthenticatedUser()));
       for(int i=0;i<complaintsDto.size();i++)
       {   
       	if(complaintsDto.get(i).getClosingDate()!=null)
       	{
       		complaintsDto.remove(i);	
       		i--;
       	}
       	else
       	{
           	Period periode= Period.between(complaintsDto.get(i).getComplaintDate(),LocalDate.now());
           	if(periode.getDays()!=nDays)
           	{
           		complaintsDto.remove(i);	
           		i--;
           	}
       	}
       }
       if(complaintsDto.size()<1)
       {
           Paragraph p1 = new Paragraph("Il n'y a pas de réclamation en souffrance pour "+nDays+" jour(s)", font);
           p1.setAlignment(Paragraph.ALIGN_CENTER);
           document.add(p1);
           document.close(); 
       }
       else
       {
           Paragraph p1 = new Paragraph("Les réclamations en souffrances pour "+nDays+" jour(s)", font);
           p1.setAlignment(Paragraph.ALIGN_CENTER);
           document.add(p1); 
           for (ComplaintDto complaintDto : complaintsDto) 
           { 
               PdfPTable table = new PdfPTable(7);
               table.setWidthPercentage(100f);
               table.setWidths(new float[] {1.5f,2f,3f,2f,1.5f,2f,1.5f});
               table.setSpacingBefore(10);
               writeTableHeader(table);
               writeTableData(table,complaintRepository.findByPublicId(complaintDto.getPublicId()));  
               document.add(table);          
               List<Attachment>  attachments=attachmentRepository.findByComplaint(complaintRepository.findByPublicId(complaintDto.getPublicId()));  
               p1 = new Paragraph("Capture d’écran : ", font);
               p1.setAlignment(Paragraph.ALIGN_LEFT);
               document.add(p1);
               for (Attachment attachment : attachments) 
               {
                   Paragraph nomPiece = new Paragraph(attachment.getAttachmentName(),font);
                   nomPiece .setAlignment(Paragraph.ALIGN_CENTER);
                   document.add(nomPiece);
                   String pathPiece=attachment.getPath();
                   pathPiece=pathPiece.replace("/upload/","");
                   Image image =Image.getInstance(FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"\\src\\main\\resources\\upload\\"+pathPiece);
                   //image.setAbsolutePosition(100f,200f);
                   image.scaleAbsolute(520f,600f);
                   document.add(image);
                   document.newPage();
               }
               document.newPage(); 
           }
           document.close(); 
    	   
       }     
   }   
  //Vesion 3
 public void export(HttpServletResponse response,String statut) throws DocumentException, IOException, BadElementException {  
	 List<ComplaintDto> complaintsDto=complaintService.clientComplaints(clientRepository.findByUser(sharedService.getAuthenticatedUser()));
     if(!statut.equals("Toutes"))
     {
         for(int i=0;i<complaintsDto.size();i++)
         {
         	if(!complaintsDto.get(i).getStatus().equals(statut))
         	{
         		complaintsDto.remove(i);	
         		i--;
         	}
         }
     }
     Document document = new Document(PageSize.A4);
     PdfWriter.getInstance(document, response.getOutputStream());
     document.open();
     Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
     font.setSize(18);
     font.setColor(Color.BLACK);
     if(complaintsDto.size()<1)
     {
         Paragraph p1 = new Paragraph("Il n'y a pas de réclamation de cet état ", font);
         p1.setAlignment(Paragraph.ALIGN_CENTER);
         document.add(p1);
         document.close(); 
     }
     else
     {
         Paragraph p1;  
         switch(statut)
         {
             case "Saisie":
           	   p1 = new Paragraph("Les réclamations en attente", font);
           	   p1.setAlignment(Paragraph.ALIGN_CENTER);
           	   document.add(p1);
                 break;
             case "En cours de traitement":
           	  p1 = new Paragraph("Les réclamations en cours de traitement", font);
                 p1.setAlignment(Paragraph.ALIGN_CENTER);
                 document.add(p1);
                 break;
             case "Traitée et Annulée":
           	  p1 = new Paragraph("Les réclamations traitées et annulées", font);
                 p1.setAlignment(Paragraph.ALIGN_CENTER);
                 document.add(p1);
                 break;
             case "Toutes":
           	  p1 = new Paragraph("Toutes les réclamations", font);
                 p1.setAlignment(Paragraph.ALIGN_CENTER);
                 document.add(p1);
                 break;
             default :
                 return;
         } 
         for (ComplaintDto complaintDto : complaintsDto) 
         { 
             PdfPTable table = new PdfPTable(7);
             table.setWidthPercentage(100f);
             table.setWidths(new float[] {1.5f,2f,3f,2f,1.5f,2f,1.5f});
             table.setSpacingBefore(10);
             writeTableHeader(table);
             writeTableData(table,complaintRepository.findByPublicId(complaintDto.getPublicId()));  
             document.add(table);          
             List<Attachment>  attachments=attachmentRepository.findByComplaint(complaintRepository.findByPublicId(complaintDto.getPublicId())); 
             p1 = new Paragraph("Capture d’écran : ", font);
             p1.setAlignment(Paragraph.ALIGN_LEFT);
             document.add(p1);
             for (Attachment attachment : attachments) 
             {
                 Paragraph nomPiece = new Paragraph(attachment.getAttachmentName(),font);
                 nomPiece .setAlignment(Paragraph.ALIGN_CENTER);
                 document.add(nomPiece);
                 String pathPiece=attachment.getPath();
                 pathPiece=pathPiece.replace("/upload/","");
                 Image image =Image.getInstance(FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"\\src\\main\\resources\\upload\\"+pathPiece);
                 //image.setAbsolutePosition(100f,200f);
                 image.scaleAbsolute(520f,600f);
                 document.add(image);
                 document.newPage();
             }
             document.newPage(); 
         }
         document.close();   
     }
    
 }   


  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
