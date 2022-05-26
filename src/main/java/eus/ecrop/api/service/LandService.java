package eus.ecrop.api.service;

import org.springframework.data.domain.Page;

import eus.ecrop.api.domain.Land;
import eus.ecrop.api.domain.User;
import eus.ecrop.api.dto.LandDto;

/*
* @author Mikel Orobengoa
* @version 25/05/2022
*/

/*
* The LandService interface is a service interface that defines the methods that are used to manage the Land entity
*/
public interface LandService {
    public Land save(Land land);
    public Land create(LandDto landDto, User user);
    public Land update(LandDto landDto, User user);
    public Page<Land> findAllByUser(Long userId, int page, int size);
    public Land findById(Long id);
    public void delete(Land land);
    public LandDto convertToDto(Land land);
}