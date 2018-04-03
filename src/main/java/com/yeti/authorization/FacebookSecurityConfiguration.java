package com.yeti.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Configuration
public class FacebookSecurityConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FacebookSecurityConfiguration.class);
/*
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private FacebookConnectionSignup facebookConnectionSignup;
    
    @Bean
    // @Primary
    public ProviderSignInController providerSignInController() {
    	log.debug("in providerSignInController");
        ((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new FacebookSignInAdapter());
    }    
*/
}


