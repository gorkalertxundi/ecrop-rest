package eus.ecrop.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.ecrop.api.domain.User;
import eus.ecrop.api.repository.UserRepository;
import eus.ecrop.api.service.RoleService;
import eus.ecrop.api.service.UserService;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

/**
 * The UserServiceImpl class is a service class that implements the UserService
 * interface
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    /**
     * Find a user by their idToken, if they don't exist, return null.
     * 
     * @param idToken The ID token that was returned by the Google Sign-In flow.
     * @return The user object is being returned.
     */
    @Override
    public User findByIdToken(String idToken) {
        return userRepository.findByIdToken(idToken).orElse(null);
    }

    /**
     * It creates a new user, sets the role to ROLE_USER, sets enabled to true, sets
     * the idToken to the
     * idToken passed in, and then saves the user
     * 
     * @param idToken The ID token that you get from the client.
     * @return A User object.
     */
    @Override
    public User registerUser(String idToken) {
        User user = new User();
        user.setRole(roleService.findByName("ROLE_USER"));
        user.setEnabled(true);
        user.setIdToken(idToken);
        user = userRepository.save(user);
        return user;
    }

    /**
     * If the user exists, return the user, otherwise return null
     * 
     * @param id The id of the user to be deleted.
     * @return The userRepository.findById(id) is returning an Optional&lt;User&gt;.
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
