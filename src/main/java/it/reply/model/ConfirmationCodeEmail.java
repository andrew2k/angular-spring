package it.reply.model;

import java.util.Date;
import java.util.UUID;

public class ConfirmationCodeEmail{
	
	
	private Date insertDate = new Date();
	private String code;
	private String username;
	private String password;
	
	
	public ConfirmationCodeEmail(String username, String password){
		this.username=username; this.password=password;
		code = username.split("@")[0] + "-sep-" + UUID.randomUUID().toString();
	}
	
	

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
