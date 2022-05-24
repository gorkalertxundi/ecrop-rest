package eus.ecrop.api.service;

import eus.ecrop.api.domain.User;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

/*
* The UserService interface is a service interface that defines the methods that are used to manage the User entity
*/
public interface UserService {
    
    public User findByIdToken(String idToken);
    public User registerUser(String idToken);
    public User findById(Long id);

}
