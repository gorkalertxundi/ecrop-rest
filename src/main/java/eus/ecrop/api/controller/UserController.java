package eus.ecrop.api.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* @author Mikel Orobengoa
* @version 06/05/2022
*/

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public OidcUser user(@AuthenticationPrincipal OidcUser principal) {
        return principal;
    }
}