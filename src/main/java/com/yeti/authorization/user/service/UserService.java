package com.yeti.authorization.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yeti.model.host.Roles;
import com.yeti.model.host.User;
import com.yeti.core.repository.types.ContactClassificationTypeRepository;
import com.yeti.core.repository.user.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	Environment env;
	
	@Autowired
	private UpdatableBCrypt updatableBCrypt; 

	@Autowired
	@Lazy
	private UserRepository userRepository;

	ContactClassificationTypeRepository contactClassificationTypeRepository;

	@Autowired
    public UserService(@Lazy ContactClassificationTypeRepository contactClassificationTypeRepository) {
        this.contactClassificationTypeRepository = contactClassificationTypeRepository;
    }
	
	public List<User> getAllUsers() {
		List<User> companies = new ArrayList<User>();
		userRepository.findAll().forEach(companies::add);
		return companies;
	}

	public User getUser(Integer id) {
		return userRepository.findOne(id);
	}
	
	public boolean exists(Integer id) {
		return (userRepository.findOne(id) != null);
	}

	public boolean validateUser(User user) {
		User storedUser = this.getUser(user.getUsername());
		return ( storedUser != null ) && 
			updatableBCrypt.checkPassword(user.getPassword(),storedUser.getPassword());
	}
	
	
	public void deleteUser(Integer id) {
		userRepository.delete(id);
	}	
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                					user.getUsername(), 
                					user.getPassword(), 
                					AuthorityUtils.createAuthorityList("ROLE_USER")
                	))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
    }

	public User addCompletedUser(User user) {
		User addedUser = null;
		try {
			String hashedPassword = updatableBCrypt.hash(user.getPassword(), updatableBCrypt.getSalt());
			log.debug("password hashed");
			user.setPassword(hashedPassword);		
			addedUser = userRepository.save(user);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return addedUser;
	}

	public User addNewUser(User user) {
		user.setCompanyId(Integer.parseInt(env.getProperty("tenant.companyId")));
		user.setClassificationType(contactClassificationTypeRepository.getOne("HO"));
		user.setCreateDate(new Date());
		user.setActive(true);
        user.getRoles().add(Roles.USER);
		return addCompletedUser(user);
	}
	
	public User updateUser(Integer id, User user) {
		return userRepository.save(user);
	}
  
	public User getUser(String userLogin) {
		log.debug("getting user");
		Optional<User> oUser = userRepository.findByUsername(userLogin);
		if( oUser.isPresent() ) {
			log.debug("getting user: user found");
			return oUser.get();
		} else {
			log.debug("getting user: user not found");
			return null;
		}
	}
  
}
