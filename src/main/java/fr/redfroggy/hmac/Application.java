package fr.redfroggy.hmac;

import fr.redfroggy.hmac.mock.MockUsers;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Main application
 * Created by Michael DESIGAUD and Andrea Franceschini (a.franceschini@reply.it) in 2016.
 */
@SpringBootApplication(scanBasePackages={"it.reply", "fr.redfroggy.hmac"})
public class Application extends SpringBootServletInitializer{
    public static void main(String[] args) throws IOException {
    	//ConfigConstants.fillConstantsFromProperties(System.getProperty("replyRedFroggyPropFile"));
        MockUsers.mock();
        SpringApplication.run(Application.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	MockUsers.mock();
        return application.sources(Application.class);
    }
    
}
