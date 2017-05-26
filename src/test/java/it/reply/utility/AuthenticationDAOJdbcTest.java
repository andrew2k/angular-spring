package it.reply.utility;


import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import fr.redfroggy.hmac.ApplicationTest;
import fr.redfroggy.hmac.dto.UserDTO;
import it.reply.dao.jdbc.AuthenticationDAOjdbc;
import it.reply.model.ConfirmationCodeEmail;
import it.reply.model.Person;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
public class AuthenticationDAOJdbcTest {

	@Autowired JdbcTemplate jdbcTemplate;
	@Autowired AuthenticationDAOjdbc authenticationDAOjdbc;
	
	
	@SuppressWarnings("unused")
	@Test
	public void testJdbc(){
		
		new Person("dfsdf");
		
		List<Person> persons= jdbcTemplate.query("SELECT * from public.users", 
				(rs, rowNum) -> new Person(rs.getString("username")));
		
		System.out.println("dsf");
	}
	
	
	@Test
	public void testFindByUsername(){
		String username="mkyong";
		UserDTO ud = authenticationDAOjdbc.findByUsername(username);
		System.out.println(ud.getLogin());
	}
	
	
	
	
	//@Test
	public void testInsertWaitingList(){
		ConfirmationCodeEmail confirmationCodeEmail = new ConfirmationCodeEmail("pluto@reply.it", "abcd");
		authenticationDAOjdbc.insertWatingConfirmation( confirmationCodeEmail);
	}
	
	@Test
	public void testResetPwd(){
		authenticationDAOjdbc.resetPwd("pippo-sep-9dc0d684-4c80-4f1d-ae33-5f9ab8f3eac9", "mynewpwd");
	}
	
	
	/*public UserDTO findByUsername(String username){
		List<UserDTO> userDTOList = jdbcTemplate.query("SELECT * from public.users where enabled = 1 and username = ?;",
				new RowMapper<UserDTO>() {
		            public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		            	UserDTO userDTO = new UserDTO();
		            	userDTO.setLogin(rs.getString("username"));    
		            	userDTO.setPassword(rs.getString("password"));
		                return userDTO;
		            }
		}, new Object[]{username});
		if(userDTOList!=null && userDTOList.size()>0) return userDTOList.get(0);
		return null;
	}*/
	
	
}
