package com.yeti.authorization;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/account")
public class PrincipalResource {

    @RequestMapping(method = RequestMethod.GET)
    public Principal oauth(Principal principal) {
        return principal;
    }
}
