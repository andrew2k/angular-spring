package it.reply.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class MvcConfig extends WebMvcConfigurerAdapter{
	 @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/confirm_email").setViewName("confirm_email");
	    }
}
