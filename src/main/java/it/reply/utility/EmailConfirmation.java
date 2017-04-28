package it.reply.utility;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.reply.dao.AuthenticationDAO;
import it.reply.model.ConfirmationCodeEmail;

@Component
@Scope("session")
public class EmailConfirmation {

	
	@Autowired SendEmail sendEmail;
	
	@Autowired AuthenticationDAO authenticationDAO;
	

	public void sendConfirmationEmail(String toAddress, String password, String baseUrl) throws AddressException, MessagingException{
		ConfirmationCodeEmail confirmationCodeEmail = new ConfirmationCodeEmail(toAddress, password);
		authenticationDAO.insertWatingConfirmation(confirmationCodeEmail);
		
		String verificationAddress = baseUrl + "/api/confirmEmail?code=" + confirmationCodeEmail.getCode();
		
		String subject = "email verification";
		
		String emailText = "";
		emailText += "<br/><br/>" +
			"We receveid your registration request.<br/><br/>" +
			"Please confirm your registration clicking on the link below:<br/>" +
			"<a href=\""+verificationAddress+"\">Confirm Email</a><br/><br/>" +
			"Thankyou";
		
		sendEmail.sendEmail(toAddress, subject, emailText);
		

	}

}
