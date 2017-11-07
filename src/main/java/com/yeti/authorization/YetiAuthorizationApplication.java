package com.yeti.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.hateoas.config.EnableEntityLinks;

@SpringBootApplication
@EnableEntityLinks
public class YetiAuthorizationApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(YetiAuthorizationApplication.class, args);
	}
}
