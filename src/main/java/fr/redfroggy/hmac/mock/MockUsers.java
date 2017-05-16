package fr.redfroggy.hmac.mock;


import fr.redfroggy.hmac.dto.Profile;
import fr.redfroggy.hmac.dto.UserDTO;
import it.reply.model.ConfirmationCodeEmail;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Mock users
 * Created by Michael DESIGAUD on 15/02/2016.
 */
@SuppressWarnings("unchecked")
@Service
public class MockUsers {

    private static List<UserDTO> users = new ArrayList<>();
    private static  List<ConfirmationCodeEmail> waitingConfirmationList = new ArrayList<ConfirmationCodeEmail>();
    private static  List<ConfirmationCodeEmail> resetPwdConfirmationList = new ArrayList<ConfirmationCodeEmail>();
    

    @SuppressWarnings({ "serial", "rawtypes" })
	private static Map<Profile,List<String>> authorities =  new HashMap(){{
        put(Profile.ADMIN,Arrays.asList("ROLE_ADMIN","ROLE_MANAGER","ROLE_USER"));
        put(Profile.USER,Arrays.asList("ROLE_USER"));
        put(Profile.MANAGER,Arrays.asList("ROLE_MANAGER","ROLE_USER"));
    }};

    public static void mock(){
        UserDTO admin = new UserDTO();
        admin.setId(1);
        admin.setLogin("admin");
        admin.setProfile(Profile.ADMIN);
        admin.setPassword("frog");
        admin.setAuthorities(authorities.get(admin.getProfile()));
        users.add(admin);

        UserDTO user = new UserDTO();
        user.setId(2);
        user.setLogin("user");
        user.setProfile(Profile.USER);
        user.setPassword("frog");
        user.setAuthorities(authorities.get(user.getProfile()));
        users.add(user);

        UserDTO manager = new UserDTO();
        manager.setId(3);
        manager.setLogin("manager");
        manager.setProfile(Profile.MANAGER);
        manager.setPassword("frog");
        manager.setAuthorities(authorities.get(manager.getProfile()));
        users.add(manager);
        
        UserDTO user2 = new UserDTO();
        user2.setId(4);
        user2.setLogin("alex");
        user2.setProfile(Profile.USER);
        user2.setPassword("123456");
        user2.setAuthorities(authorities.get(user2.getProfile()));
        users.add(user2);

    }

    public static List<UserDTO> getUsers(){
        if(users.isEmpty()){
            mock();
        }
        return users;
    }

    /**
     * Find a user by username
     * @param username username
     * @return a UserDTO if found, null otherwise
     */
    public static UserDTO findByUsername(String username){
        for(UserDTO userDTO : users){
            if(userDTO.getLogin().equals(username)){
                return userDTO;
            }
        }
        return null;
    }

    /**
     * Find a user by id
     * @param id user id
     * @return a UserDTO if found, null otherwise
     */
    public static UserDTO findById(Integer id){
        for(UserDTO userDTO : users){
            if(userDTO.getId().equals(id)){
                return userDTO;
            }
        }
        return null;
    }

    /**
     * Update a given user
     * @param newUserDTO new user
     * @return updated user
     */
    public static UserDTO update(UserDTO newUserDTO){
        UserDTO existingUser = findById(newUserDTO.getId());
        if(existingUser != null){
            BeanUtils.copyProperties(newUserDTO,existingUser,"password","publicSecret");
            existingUser.setAuthorities(authorities.get(existingUser.getProfile()));
        }
        return existingUser;
    }
    
    public static UserDTO createUser(String username, String password){
    	UserDTO user = new UserDTO();
    	user.setId(users.size());
    	user.setLogin(username);
    	user.setProfile(Profile.USER);
    	user.setPassword(password);
    	user.setAuthorities(authorities.get(user.getProfile()));
        users.add(user);
        return user;
       // inMemoryUserDetailsManager.createUser(new User(username, password, new ArrayList<GrantedAuthority>()));
       /*SecurityConfiguration.getConfigurer().withUser(username)
        .password(passwordEncoder().encode(password))
        .roles(Profile.USER.name());*/
       // SecurityConfiguration.addInMemoryUser(username, password);
    }
    
    public static UserDTO loadUserByCode(String code){
    	synchronized(waitingConfirmationList) {
			ListIterator<ConfirmationCodeEmail> itr = waitingConfirmationList.listIterator();
			while(itr.hasNext()){
				ConfirmationCodeEmail confirmationCodeEmail = itr.next();
				if(confirmationCodeEmail.getCode().equals(code)) {
					itr.remove();
					return createUser( confirmationCodeEmail.getUsername(), confirmationCodeEmail.getPassword());
				}
			}
		}
    	return null;
    }
    
    public static void insertWatingConfirmation(ConfirmationCodeEmail confirmationCodeEmail){
		waitingConfirmationList.add(confirmationCodeEmail);
		cleanOldCodes();
	}
    
    public static void insertResetPwdConfirmation(ConfirmationCodeEmail confirmationCodeEmail){
    	resetPwdConfirmationList.add(confirmationCodeEmail);
		cleanOldCodesPwd();
	}
    
    public static boolean resetPwd(String code, String pwd){
    	boolean passwordChanged = false;
    	synchronized(MockUsers.class) {
			ListIterator<ConfirmationCodeEmail> itr = resetPwdConfirmationList.listIterator();
			while(itr.hasNext()){
				ConfirmationCodeEmail confirmationCodeEmail = itr.next();
				if(confirmationCodeEmail.getCode().equals(code)) {
					UserDTO userDTO = findByUsername(confirmationCodeEmail.getUsername());
					if(userDTO!=null){
						userDTO.setPassword(pwd);
						passwordChanged=true;
						itr.remove();						
					}
				}
			}
		}
    	return passwordChanged;
    }
    
    private static void cleanOldCodes(){
		// clean all verification codes that are older than 7 days
		synchronized(MockUsers.class) {
			ListIterator<ConfirmationCodeEmail> itr = waitingConfirmationList.listIterator();
			while(itr.hasNext()){
				if(itr.next().getInsertDate().getTime() <= (new Date().getTime() - (7*1000*60*60*24)))
					itr.remove();
			}
		}
	}
    
    private static void cleanOldCodesPwd(){
		// clean all verification codes that are older than 7 days
		synchronized(MockUsers.class) {
			ListIterator<ConfirmationCodeEmail> itr = resetPwdConfirmationList.listIterator();
			while(itr.hasNext()){
				if(itr.next().getInsertDate().getTime() <= (new Date().getTime() - (1000*60*60*6)))
					itr.remove();
			}
		}
	}
    
   
    
    
}
