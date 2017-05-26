package it.reply.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import it.reply.dao.AuthenticationDAO;
import it.reply.dao.jdbc.AuthenticationDAOjdbc;

@Configuration
@Profile("jdbc")
public class ConfigJdbcProfile {

	@Autowired AuthenticationDAOjdbc authenticationDAOjdbc;
	
	
	@Bean
	public AuthenticationDAO authenticationDAO(){
		return authenticationDAOjdbc;
	}
}
