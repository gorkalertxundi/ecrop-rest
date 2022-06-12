package eus.ecrop.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.ecrop.api.domain.User;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

/*
* A repository interface for the User entity.
*/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their idToken, if they don't exist, return null;
     * 
     * @param idToken The idToken of the user to find.
     * @return Optional&lt;User&gt;
     */
    public Optional<User> findByIdToken(String idToken);

    public Optional<User> findByApiKey(String apiKey);

}
