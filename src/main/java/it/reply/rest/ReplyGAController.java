package it.reply.rest;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.redfroggy.hmac.mock.MockUsers;
import it.reply.dao.AuthenticationDAO;
import it.reply.model.Captcha;
import it.reply.model.Person;
import it.reply.utility.EmailConfirmation;

/*
 * author: Andrea Franceschini (a.franceschini@reply.it)
 * 
 */

@RestController
@Scope("session")
public class ReplyGAController {
	
	@Autowired
	Captcha captcha;

	@Autowired
	EmailConfirmation emailConfirmation;
	
	@Autowired
	MockUsers mockUsers;
	
	@Autowired AuthenticationDAO authenticationDAO;
	
	/*private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    public ReplyGAController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
       this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }*/
	
	
	/*@RequestMapping(value="/api/register", produces="application/json", method = RequestMethod.POST)
    public Person greeting(@RequestBody Person pr) {
        
		System.out.println(pr.getEmail());
		
		Person p = new Person();
		p.setEmail("cigo@ciccio.it");
		p.setName("ciao");
		p.setSurname("ecco");
		p.setPassword1("aaaa");
		p.setPassword2("bbbb");
		return p;
    }*/
	
	
	@RequestMapping(value="/api/register", produces="application/json", method = RequestMethod.POST)
    public String register(HttpServletRequest request, @RequestBody Person pr) {
		// controllare due passwords uguali
		
		
		String returnedCode="registration_completed";
		if(! pr.getCaptcha().equals(captcha.getCaptchaToken())){
			returnedCode="wrong_captcha";
		}else{
			try {
				emailConfirmation.sendConfirmationEmail(pr.getEmail(), pr.getPassword1(),
						request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()));
			} catch (MessagingException e) {
				e.printStackTrace();
				returnedCode="wrong_email";
			}
		}
		
		return returnedCode;
    }
	
	@RequestMapping(value="/api/forgotPwd", produces="application/json", method = RequestMethod.POST)
    public String forgotPwd(HttpServletRequest request, @RequestBody Person pr) {
		// controllare due passwords uguali
		
		/* In this case, the captcha variable contains the code */
		String returnedCode="error";
		if(pr.getCaptcha().length()>3 && pr.getEmail()==null){
			// reset the password
			boolean passwordChanged = authenticationDAO.resetPwd(pr.getCaptcha(), pr.getPassword1());
			if(passwordChanged) returnedCode="password_changed";
		}else{
			//TODO check that the user is already registered !!!!
			
			// send the email to reset the password
			try {
				emailConfirmation.resetPwdEmail(pr.getEmail(), pr.getPassword1(),
						request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()));
				returnedCode="email_sent";
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
		return returnedCode;
    }
	
	
	@RequestMapping(value="/api/showCaptcha", method = RequestMethod.GET)
	public void showCaptcha(HttpServletResponse response) throws IOException{
		response.setHeader("Cache-Control", "no-store");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setContentType("image/jpeg");
	    ServletOutputStream responseOutputStream = response.getOutputStream();
	    responseOutputStream.write(captcha.getImg());
	    responseOutputStream.flush();
	    responseOutputStream.close();
	 }
		
	
	@RequestMapping(value="/api/confirmEmail", method = RequestMethod.GET)
	public String confirmEmail(HttpServletRequest request, @RequestParam(value = "code", required = false) String code) throws IOException{
		String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
		String url = baseUrl + "#/authenticate/" + code;
		return "<meta http-equiv=\"refresh\" content=\"1; url=" + url + "\">";
	 }	
	
	
	
}
