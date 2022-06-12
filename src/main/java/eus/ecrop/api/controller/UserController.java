package eus.ecrop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.ecrop.api.domain.User;
import eus.ecrop.api.service.UserService;

/*
* @author Mikel Orobengoa
* @version 06/05/2022
*/

@RestController
@RequestMapping(path = "/user", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userService.convertToDto(user), HttpStatus.OK);
    }
}