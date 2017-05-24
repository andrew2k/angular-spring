package it.reply.dao;


import java.util.Collection;

import fr.redfroggy.hmac.dto.UserDTO;
import it.reply.model.ConfirmationCodeEmail;


public interface AuthenticationDAO {

	
	public Collection<UserDTO> getUsers();
	
	public UserDTO findById(Integer id);
	
	public UserDTO update(UserDTO userDTO);
	
	public UserDTO findByUsername(String username);
	
	public UserDTO loadUserByCode(String code);
	
	public void insertWatingConfirmation(ConfirmationCodeEmail confirmationCodeEmail);
	
	public void insertResetPwdConfirmation(ConfirmationCodeEmail confirmationCodeEmail);
	
	public boolean resetPwd(String code, String pwd);
	
	
}
