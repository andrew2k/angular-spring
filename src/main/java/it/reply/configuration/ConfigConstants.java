package it.reply.configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigConstants {


	//private static String captchaD;
	
	
	
	

	public static synchronized void fillConstantsFromProperties(String propFilePath) throws IOException{
		@SuppressWarnings("unused")
		Properties prop = getProperties(propFilePath);
		
		//captchaD = prop.getProperty("captchaD");
		
	}

	private static Properties getProperties(String propFilePath) throws IOException {
		Properties prop = new Properties();
		prop.load(new BufferedReader(new FileReader(propFilePath)));
		return prop;
	}
	
	
	//public static String getCaptchaD() {return captchaD;}
	
	
}
