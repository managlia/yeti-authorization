package com.yeti.authorization.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.yeti.authorization.model.host.User;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "User", path = "Users")
public interface UserRepository extends JpaRepository<User, Integer> {

	 Optional<User> findByUsername(String userLogin);  //Entity, Id
	
}
