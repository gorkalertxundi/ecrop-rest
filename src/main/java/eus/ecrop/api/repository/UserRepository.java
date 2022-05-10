package eus.ecrop.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.ecrop.api.domain.User;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    public Optional<User> findByIdToken(String idToken);

}
