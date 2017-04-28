package fr.redfroggy.hmac.service;

import fr.redfroggy.hmac.configuration.security.SecurityUser;
import fr.redfroggy.hmac.dto.UserDTO;
import it.reply.dao.AuthenticationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Hmac user details service
 * Created by Andrea Franceschini (a.franceschini@reply.it)
 */
@Component
public class HmacUserDetailsService implements UserDetailsService{

	@Autowired
	AuthenticationDAO authenticationDAO;
	
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = authenticationDAO.findByUsername(username);
        return loadUserFromDTO(userDTO, username);
    }
    
    
    public UserDetails loadUserByCode(String code){
    	UserDTO userDTO = authenticationDAO.loadUserByCode(code);
    	return loadUserFromDTO(userDTO, code);
    }
    
    private UserDetails loadUserFromDTO(UserDTO userDTO, String username){
    	if (userDTO == null) {
            throw new UsernameNotFoundException("User "+username+" not found");
        }
    		//System.out.println("ECCOMI IN  HmacUserDetailsService:  " + username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(!userDTO.getAuthorities().isEmpty()){
            for(String authority : userDTO.getAuthorities()){
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        return new SecurityUser(userDTO.getId(),userDTO.getLogin(),userDTO.getPassword(),userDTO.getProfile(),authorities);
    
    }
    
}
