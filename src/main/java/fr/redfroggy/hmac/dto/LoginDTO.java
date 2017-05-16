package fr.redfroggy.hmac.dto;


/**
 * Login DTP
 * Created by Michael DESIGAUD on 14/02/2016.
 */
public class LoginDTO {

    private String login;

    private String password;
    
    private String code;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
