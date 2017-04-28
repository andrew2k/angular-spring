package it.reply.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.reply.utility.EmailConfirmation;

@Controller
@Scope("session")
public class JspController {

	@Autowired
	EmailConfirmation emailConfirmation;
	
	/*@RequestMapping(value="/api/confirmEmailJsp", method = RequestMethod.GET)
	public String confirmEmailJsp(@RequestParam(value = "code", required = false) String code, ModelMap model) throws IOException{
		if(code!=null && !code.equals("") && emailConfirmation.verifyCode(code))
			model.addAttribute("code_status", "code_ok");
		else model.addAttribute("code_status", "error2");
		return "confirm_email";
	 }	*/
	
	
}
