package eus.ecrop.api.service;

import eus.ecrop.api.domain.User;
import eus.ecrop.api.dto.UserDto;

/*
* @author Mikel Orobengoa
* @version 10/06/2022
*/

/*
* The UserService interface is a service interface that defines the methods that are used to manage the User entity
*/
public interface UserService {

    public User findByIdToken(String idToken);

    public User registerUser(String idToken, String name, String imageUrl);

    public User findById(Long id);

    public User save(User user);

    public UserDto convertToDto(User user);

    public User updateUser(Long id, String name, String picture);

    public User findByApiKey(String apiKey);

}
