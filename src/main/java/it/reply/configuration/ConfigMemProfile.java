package it.reply.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import it.reply.dao.AuthenticationDAO;
import it.reply.dao.AuthenticationDAOmem;

@Configuration
@Profile("memory")
public class ConfigMemProfile {

	
	@Autowired AuthenticationDAOmem authenticationDAOmem;
	
	
	@Bean
	public AuthenticationDAO authenticationDAO(){
		return authenticationDAOmem;
	}
	
	
}
