package it.reply.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import fr.redfroggy.hmac.dto.UserDTO;

public class UsersRowMapper implements RowMapper<UserDTO>{

	
	public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	UserDTO userDTO = new UserDTO();
    	userDTO.setId(rs.getInt("user_id"));
    	userDTO.setLogin(rs.getString("username"));    
    	userDTO.setPassword(rs.getString("password"));
    	userDTO.setProfile(rs.getString("profile"));
        return userDTO;
    }
	
	
}
