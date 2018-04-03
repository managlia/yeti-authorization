package com.yeti.authorization.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class UpdatableBCrypt {
    private static final Logger log = LoggerFactory.getLogger(UpdatableBCrypt.class);

    private final int logRounds = 10;

    public UpdatableBCrypt() {
    }

    public String hash(String password, String salt) {
    	log.debug("Get hash");
        return BCrypt.hashpw(password, salt);
    }

    public String getSalt() {
    	log.debug("Get salt");
        return BCrypt.gensalt(logRounds);
    }

    public boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;
		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);
		return(password_verified);
	}    
    
}