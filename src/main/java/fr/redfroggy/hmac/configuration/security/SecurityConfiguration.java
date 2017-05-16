package fr.redfroggy.hmac.configuration.security;

import fr.redfroggy.hmac.configuration.security.hmac.HmacRequester;
import fr.redfroggy.hmac.configuration.security.hmac.HmacSecurityConfigurer;
import fr.redfroggy.hmac.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Security configuration
 * Created by Michael DESIGAUD on 14/02/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private HmacRequester hmacRequester;
    
    
    @Autowired
    private UserDetailsService userDetailsService;
    

    

	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**/*.{js}")
                .antMatchers("/node_modules/**")
                .antMatchers("/assets/**")
                .antMatchers("*.{ico}")
                .antMatchers("/views/**/*.{html}")
                .antMatchers("/app/**/*.{html}")
                .antMatchers("/api/showCaptcha")
                .antMatchers("/api/register")
                .antMatchers("/api/confirmEmail")
                .antMatchers("/api/confirmEmailJsp")
                .antMatchers("/api/confirm_email")
                .antMatchers("/api/forgotPwd");
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/api/authenticate").anonymous()
                    //.antMatchers("/reply-webseed-angular2-1.0/api/authenticate").anonymous()
                    .antMatchers("/api/register").anonymous()
                    .antMatchers("/api/showCaptcha").anonymous()
                    .antMatchers("/api/confirmEmail").anonymous()
                    .antMatchers("/api/confirmEmailJsp").anonymous()
                    .antMatchers("/api/confirm_email").anonymous()
                    .antMatchers("/").anonymous()
                    .antMatchers("/favicon.ico").anonymous()
                    .antMatchers("/api/**").authenticated()
                   // .antMatchers("/reply-webseed-angular2-1.0/api/**").authenticated()
                .and()
                .csrf()
                    .disable()
                    .headers()
                    .frameOptions().disable()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                    .permitAll()
                .and()
                    .apply(authTokenConfigurer())
                .and()
                    .apply(hmacSecurityConfigurer());
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer configurer = auth
                .inMemoryAuthentication()
                    .passwordEncoder(passwordEncoder());

        for(UserDTO userDTO : MockUsers.getUsers()) {
            configurer.withUser(userDTO.getLogin())
                    .password(passwordEncoder().encode(userDTO.getPassword()))
                    .roles(userDTO.getProfile().name());
        }
    }*/
      
    
    
 @Override
 public void configure(AuthenticationManagerBuilder auth) throws Exception {

	/*JdbcUserDetailsManagerConfigurer configurer = auth.jdbcAuthentication().passwordEncoder(passwordEncoder());
	 
	 configurer.dataSource(dataSource).usersByUsernameQuery(
			 "select username,password, enabled from users where username=?")
	 .authoritiesByUsernameQuery("select username, role from user_roles where username=?");*/
	 
	 auth.userDetailsService(userDetailsService);

 } 
     

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private HmacSecurityConfigurer hmacSecurityConfigurer(){
        return new HmacSecurityConfigurer(hmacRequester);
    }

    private XAuthTokenConfigurer authTokenConfigurer(){
        return new XAuthTokenConfigurer(authenticationService);
    }
    

}
