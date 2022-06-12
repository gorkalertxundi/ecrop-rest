package eus.ecrop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.ecrop.api.domain.User;
import eus.ecrop.api.dto.SubscriptionDto;
import eus.ecrop.api.service.SubscriptionService;

/*
* @author Mikel Orobengoa
* @version 06/06/2022
*/

@RestController
@RequestMapping(path = "subscription", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(Authentication authentication, @RequestBody SubscriptionDto subscriptionDto) {
        User user = (User) authentication.getPrincipal();
        subscriptionService.activateSubscription(user.getId(), subscriptionDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        subscriptionService.cancelSubscription(user.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}