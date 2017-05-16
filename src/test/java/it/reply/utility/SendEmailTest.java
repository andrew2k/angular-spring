package it.reply.utility;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.redfroggy.hmac.ApplicationTest;

/**
 * Authentication integration unit tests
 * Created by Andrea Franceschini on 16/02/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class SendEmailTest {
	
	@Autowired
	SendEmail sendEmail;
	
	@Test
	public void sendEmail() throws AddressException, MessagingException{
		
		sendEmail.sendEmail("atariwwwww@gmail.com", "subject1", "<a href=\"http://google.com\">subject1</a>");
		
	}
	
	

}
