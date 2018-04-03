package com.yeti.authorization;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import  org.springframework.social.facebook.api.Facebook;

import com.yeti.authorization.user.factory.YetiUserFactory;
import com.yeti.authorization.user.service.UserService;
import com.yeti.model.host.Roles;
import com.yeti.model.host.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    private static final Logger log = LoggerFactory.getLogger(FacebookConnectionSignup.class);
	
	@Autowired
    private UserService userService;
	
	@Autowired
	YetiUserFactory yetiUserFactory;

    @Override
    public String execute(Connection<?> connection) {
        log.debug("signup === " + connection.getDisplayName());

        Facebook facebook = (Facebook) connection.getApi();
        String [] fields = { "id", "email", "first_name", "last_name",
                			 "locale", "timezone", "link" };
        org.springframework.social.facebook.api.User userProfile = 
        		facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);        
        log.debug( "dfm getId:       " + userProfile.getId() );
        log.debug( "dfm Email:       " + userProfile.getEmail() );
        log.debug( "dfm FirstName:   " + userProfile.getFirstName() );
        log.debug( "dfm LastName:    " + userProfile.getLastName() );
        log.debug( "dfm getLocale:   " + userProfile.getLocale() );
        log.debug( "dfm getTimezone: " + userProfile.getTimezone() );
        log.debug( "dfm getLink:     " + userProfile.getLink() );
    	String facebookId = "FB:" + userProfile.getId();
        User returnUser = userService.getUser(facebookId);
        if( returnUser == null ) {
        	String firstName = userProfile.getFirstName();
        	String lastName = userProfile.getLastName();
        	String emailAddress = userProfile.getEmail();
            final User user = yetiUserFactory.createYetiUser(firstName, lastName, emailAddress);
            user.setUsername(facebookId);
            user.setPassword(randomAlphabetic(10));
            user.getRoles().add(Roles.USER);
            returnUser = userService.addCompletedUser(user);
        }
        return returnUser.getUsername();
    }
}