package it.reply.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import fr.redfroggy.hmac.dto.UserDTO;
import it.reply.model.ConfirmationCodeEmail;

/**
 * Users resource
 * Created by Andrea Franceschini on 15/05/2017.
 */
@Service
public class AuthenticationDAOjdbc implements AuthenticationDAO {

	
	@Autowired JdbcTemplate jdbcTemplate;
	
	
	@Override
	public Collection<UserDTO> getUsers() {
		List<UserDTO> userDTOList = jdbcTemplate.query("SELECT * from public.users where enabled = 1;",
				new RowMapper<UserDTO>() {
		            public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	UserDTO userDTO = new UserDTO();
		            	userDTO.setLogin(rs.getString("username"));    
		            	userDTO.setPassword(rs.getString("password"));
		            	userDTO.setProfile(rs.getString("profile"));
		                return userDTO;
		            }
		});
		
		for(UserDTO userDTO : userDTOList) loadAuthorities(userDTO);
		return userDTOList;
	}

	@Override
	public UserDTO findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO findByUsername(String username){
		/*List<Person> persons= jdbcTemplate.query("SELECT * from public.users", 
				(rs, rowNum) -> new Person(rs.getString("username")));
		*/
		List<UserDTO> userDTOList = jdbcTemplate.query("SELECT * from public.users where enabled = 1 and username = ?;",
				new RowMapper<UserDTO>() {
		            public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	UserDTO userDTO = new UserDTO();
		            	userDTO.setLogin(rs.getString("username"));    
		            	userDTO.setPassword(rs.getString("password"));
		            	userDTO.setProfile(rs.getString("profile"));
		                return userDTO;
		            }
		}, new Object[]{username});
		
		
		if(userDTOList!=null && userDTOList.size()>0) {
			loadAuthorities(userDTOList.get(0));
			return userDTOList.get(0);
		}
		return null;
	}
	
	
	/*
	 * Load authorities into the UserDTO (it requires that the 'login' and 'profile' field are set in the UserDTO)
	 */
	private void loadAuthorities(UserDTO userDTO){
		List<String> authoritiesList = jdbcTemplate.query("SELECT role from public.user_roles where username = ?;",
				new RowMapper<String>() { public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	String authority = rs.getString("role");  return authority; }
		}, new Object[]{userDTO.getLogin()});
		
		List<String> authoritiesList2 = jdbcTemplate.query("SELECT role from public.profile_roles where profile = ?;",
				new RowMapper<String>() { public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	String authority = rs.getString("role");  return authority; }
		}, new Object[]{userDTO.getProfile()});
		
		Set<String> authoritiesColl = new HashSet<String>(authoritiesList);
		authoritiesColl.addAll(authoritiesList2);
		userDTO.setAuthorities(new ArrayList<String>(authoritiesColl));	
	}
	

	@Override
	public UserDTO loadUserByCode(String code) {

		List<UserDTO> userDTOList = jdbcTemplate.query("SELECT * from public.registratino_waiting_list where code = ?;",
				new RowMapper<UserDTO>() {
		            public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	UserDTO userDTO = new UserDTO();
		            	userDTO.setLogin(rs.getString("username"));    
		            	userDTO.setPassword(rs.getString("password"));
		                return userDTO;
		            }
		}, new Object[]{code});
		
		UserDTO user = userDTOList.get(0);
		user.setProfile("USER_PROFILE");
		
		jdbcTemplate.update("insert into public.users(username, password, enabled, profile) values (?,?,?,?)", 
				user.getLogin(), user.getPassword(), 1, user.getProfile());

		loadAuthorities(user);
		
		return user;
	}

	@Override
	public void insertWatingConfirmation(ConfirmationCodeEmail confirmationCodeEmail) {
		jdbcTemplate.update("insert into public.registratino_waiting_list(username, password, code) values (?,?,?)", 
				confirmationCodeEmail.getUsername(), confirmationCodeEmail.getPassword(), confirmationCodeEmail.getCode());
		jdbcTemplate.update("delete from public.registratino_waiting_list where insert_date <= ?", 
				new Date(System.currentTimeMillis()-24*60*60*1000));
	}

	@Override
	public void insertResetPwdConfirmation(ConfirmationCodeEmail confirmationCodeEmail) {
		insertWatingConfirmation(confirmationCodeEmail);
	}

	@Override
	public boolean resetPwd(String code, String pwd) {
		
		String username = this.jdbcTemplate.queryForObject(
		        "select username from public.registratino_waiting_list where code = ?", String.class, code);
		
		int rows = jdbcTemplate.update("update public.users set password=? where username=?", pwd, username);
		
		if(rows==1) return true;
		return false;
	}

	
	
	
	
	
	
}
