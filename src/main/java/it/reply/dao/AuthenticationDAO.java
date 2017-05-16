package it.reply.dao;


import org.springframework.stereotype.Service;
import fr.redfroggy.hmac.dto.UserDTO;
import fr.redfroggy.hmac.mock.MockUsers;
import it.reply.model.ConfirmationCodeEmail;

@Service
public class AuthenticationDAO {

	
	
	
	
	public UserDTO findByUsername(String username){
		
		return MockUsers.findByUsername(username);
		
	}
	
	
	public UserDTO loadUserByCode(String code){
		// find user by code
		// register user
		// clean user in confirmation code table
		
		return MockUsers.loadUserByCode(code);
		
	}
	
	public void insertWatingConfirmation(ConfirmationCodeEmail confirmationCodeEmail){
		MockUsers.insertWatingConfirmation(confirmationCodeEmail);
		// remember to clean all codes !!! 
	}
	
	public void insertResetPwdConfirmation(ConfirmationCodeEmail confirmationCodeEmail){
		MockUsers.insertResetPwdConfirmation(confirmationCodeEmail);
		// remember to clean all codes !!! 
	}
	
	public boolean resetPwd(String code, String pwd){
		return MockUsers.resetPwd(code, pwd);
	}
	
	
}
