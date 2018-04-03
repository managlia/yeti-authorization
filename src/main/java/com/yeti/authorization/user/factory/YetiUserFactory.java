package com.yeti.authorization.user.factory;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.yeti.core.repository.types.ContactClassificationTypeRepository;
import com.yeti.model.host.User;

@Component
public class YetiUserFactory {

	@Autowired
	Environment env;
	
	ContactClassificationTypeRepository contactClassificationTypeRepository;

	@Autowired
    public YetiUserFactory(@Lazy ContactClassificationTypeRepository contactClassificationTypeRepository) {
        this.contactClassificationTypeRepository = contactClassificationTypeRepository;
    }
	
	public User createYetiUser(String firstName, String lastName, String emailAddress) {
		User user = new User();
		user = this.populateUser(user, firstName, lastName, emailAddress);
		return user;
	}
	
	public final User populateUser(User user, String firstName, String lastName, String emailAddress) {
		user.setCompanyId(Integer.parseInt(env.getProperty("tenant.companyId")));
		user.setClassificationType(contactClassificationTypeRepository.getOne("HO"));
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setContactEmailAddress(emailAddress);
		user.setEmailAddress(emailAddress);
		user.setCreateDate(new Date());
		user.setActive(true);
		return user;
	}

}
