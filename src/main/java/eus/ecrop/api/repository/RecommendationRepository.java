package eus.ecrop.api.repository;

import org.springframework.stereotype.Repository;

import eus.ecrop.api.domain.Land;

/*
* @author Mikel Orobengoa
* @version 08/06/2022
*/

/*
* A repository interface for the Land entity.
*/
@Repository
public interface RecommendationRepository {

    public String getRecommendation(Land land);

}