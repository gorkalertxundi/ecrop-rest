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

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Override
    public User findByIdToken(String idToken) {
        return userRepository.findByIdToken(idToken).orElse(null);
    }

    @Override
    public User registerUser(String idToken) {
        User user = new User();
        user.setRole(roleService.findByName("ROLE_USER"));
        user.setEnabled(true);
        user.setIdToken(idToken);
        user = userRepository.save(user);
        return user;
    }
}
