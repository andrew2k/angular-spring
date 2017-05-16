package it.reply.utility;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Andrea Franceschini on 16/02/2017.
 */
@Component
public class SendEmail {

	@Value("${email.smtp.host}")
    String host;
	
	@Value("${email.smtp.from}")
    String from;
	
	@Value("${email.smtp.port}")
	String port;
	
	@Value("${email.smtp.user}")
    String user;
    
	@Value("${email.smtp.password}")
    String password;
	
	
	public void sendEmail(String toAddress, String subject, String emailText) throws AddressException, MessagingException{
		Properties properties = System.getProperties();

	    // Setup mail server
	    properties.setProperty("mail.smtp.host", host);
	    properties.setProperty("mail.user", user);
	    properties.setProperty("mail.password", password);
	    properties.setProperty("mail.smtp.port", port);
	    properties.setProperty("mail.smtp.auth", "true");
	    properties.setProperty("mail.smtp.starttls.enable", "true");
	    properties.setProperty("mail.smtp.socketFactory.port", port);
	    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    properties.setProperty("mail.smtp.socketFactory.fallback", "false");
	      
	      
	    Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {  
	        protected PasswordAuthentication getPasswordAuthentication() {  
	       	    return new PasswordAuthentication(user, password);  
	        }  
	    });
	
	    
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
         message.setContent(emailText, "text/html; charset=utf-8");
         message.setSubject(subject);
         //message.setText(emailText);
         Transport.send(message);
		
	}
	
	
   
}