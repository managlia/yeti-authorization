package com.yeti.authorization.user.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.yeti.authorization.user.service.UserService;
import com.yeti.model.host.User;
import com.yeti.model.util.Batch;

@RestController
@ExposesResourceFor(User.class)
@RequestMapping(value = "/Users", produces = "application/hal+json")
public class UserController {

   private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping
    //@PreAuthorize("#oauth2.hasScope('read')")
	public ResponseEntity<List<Resource<User>>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		if( users != null ) {
			List<Resource<User>> returnUsers = new ArrayList<Resource<User>>();
			for( User user : users ) {
				returnUsers.add(getUserResource(user));
			}
			return ResponseEntity.ok(returnUsers);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Resource<User>> getUser(@PathVariable Integer id) {
		User user = userService.getUser(id);
		if( user == null ) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(getUserResource(user));
		}
	}

	@PostMapping
	public ResponseEntity<Resource<User>> addUser(@RequestBody User user, HttpServletRequest request ) {
		User storedUser = userService.getUser(user.getUsername());
		if( storedUser == null ) {
			User newUser = userService.addNewUser(user);
			if( newUser != null ) {
				String requestURI = request.getRequestURI();
				try {
					return ResponseEntity.created(new URI(requestURI + "/" + newUser.getContactId())).build();		
				} catch( Exception e ) {
					return ResponseEntity.badRequest().build();
				}
			} else {
				return ResponseEntity.badRequest().build();
			}
		} else {
			log.debug( "user id already in use" );
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/validateUser")
	public boolean validateUser(@RequestBody User user ) {
		return userService.validateUser(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Resource<User>> updateUser(@RequestBody User user, @PathVariable Integer id) {
		if( !userService.exists(id) ) {
			return ResponseEntity.notFound().build();
		} else {
			userService.updateUser(id, user);
			User updatedUser = userService.updateUser(id, user);
			if( updatedUser != null ) {
				return ResponseEntity.accepted().build();		
			} else {
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Resource<User>> deleteUser(@PathVariable Integer id) {
		if( !userService.exists(id) ) {
			return ResponseEntity.notFound().build();
		} else {
			userService.deleteUser(id);
			if( !userService.exists(id) ) {
				return ResponseEntity.accepted().build();		
			} else {
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@PatchMapping
	public void processBatchAction(@RequestBody Batch batch) {
		//userService.processBatchAction(batch);
	}
	
	private Resource<User> getUserResource(User a) {
	    Resource<User> resource = new Resource<User>(a);
	    resource.add(linkTo(methodOn(UserController.class).getUser(a.getContactId())).withSelfRel());
	    return resource;
	}

}








