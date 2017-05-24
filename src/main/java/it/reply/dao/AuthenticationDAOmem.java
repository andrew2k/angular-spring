package it.reply.dao;


import java.util.Collection;

import org.springframework.stereotype.Service;
import fr.redfroggy.hmac.dto.UserDTO;
import fr.redfroggy.hmac.mock.MockUsers;
import it.reply.model.ConfirmationCodeEmail;

@Service
public class AuthenticationDAOmem implements AuthenticationDAO{

	
	
	
	
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


	@Override
	public Collection<UserDTO> getUsers() {
		return MockUsers.getUsers();
	}


	@Override
	public UserDTO findById(Integer id) {
		return MockUsers.findById(id);
	}


	@Override
	public UserDTO update(UserDTO userDTO) {
		return MockUsers.update(userDTO);
	}
	
	
}
