package eus.ecrop.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import eus.ecrop.api.domain.Land;

/*
* @author Mikel Orobengoa
* @version 25/05/2022
*/

/*
* A repository interface for the Land entity.
*/
@Repository
public interface LandRepository extends JpaRepository<Land, Long>, JpaSpecificationExecutor<Land> {

    /**
     * Finds a Land by its name
     * 
     * @param name The name of the method.
     * @return Optional&lt;Land&gt of the requested land.
     */
    public Optional<Land> findByName(String name);

}