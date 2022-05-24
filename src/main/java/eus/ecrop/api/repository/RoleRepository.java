package eus.ecrop.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.ecrop.api.domain.Role;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

/*
* A repository interface for the Role entity.
*/
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Optional<Role> findByName(String name);

}