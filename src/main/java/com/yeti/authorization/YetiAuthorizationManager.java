package com.yeti.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.yeti.authorization.user.service.UserService;

@Configuration
public class YetiAuthorizationManager {

    private static final Logger log = LoggerFactory.getLogger(YetiAuthorizationManager.class);
	
	@Autowired
    private UserService userService;
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth
        .userDetailsService(userService)
        .passwordEncoder(passwordEncoder());
    }	
	
}
