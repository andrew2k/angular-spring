package fr.redfroggy.hmac.rest;

import fr.redfroggy.hmac.dto.Profile;
import fr.redfroggy.hmac.dto.UserDTO;
import it.reply.dao.AuthenticationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Users resource
 * Created by Michael DESIGAUD on 15/02/2016.
 */
@RestController
@RequestMapping(value = "/api")
public class Users {

	@Autowired AuthenticationDAO authenticationDAO;
	
    @RequestMapping("/users")
    public Collection<UserDTO> query(){
        return authenticationDAO.getUsers();
    }

    @RequestMapping("/users/{id}")
    public UserDTO query(@PathVariable Integer id){
        return authenticationDAO.findById(id);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public UserDTO update(@RequestBody @Valid UserDTO userDTO){
        return authenticationDAO.update(userDTO);
    }

    @RequestMapping("/users/profiles")
    public List<String> getProfiles(){
        List<String> profiles = new ArrayList<>();
        for(Profile profile: Profile.values()){
            profiles.add(profile.name());
        }
        return profiles;
    }
}
