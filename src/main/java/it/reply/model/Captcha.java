package it.reply.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.cage.Cage;
import com.github.cage.token.RandomTokenGenerator;

@Component
@Scope("session")
public class Captcha {

	private String captchaToken = null;
	private Cage cage = new Cage(null, null, null, null, 0.5f, new RandomTokenGenerator(new Random(), 6), null);
	
	
	//http://www.jadebaboon.com/wp/return-image-as-responsebody/
	public byte[] getImg() throws IOException{
		captchaToken = cage.getTokenGenerator().next();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        cage.draw(captchaToken, bos);
        return bos.toByteArray();
	}
	
	public String getCaptchaToken() {
		return captchaToken;
	}

	
}
