package eus.ecrop.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import eus.ecrop.api.domain.Land;
import eus.ecrop.api.domain.User;
import eus.ecrop.api.dto.LandDto;
import eus.ecrop.api.repository.LandRepository;
import eus.ecrop.api.service.LandService;

/*
* @author Mikel Orobengoa
* @version 26/05/2022
*/

/**
 * The LandServiceImpl class is a service class that implements the LandService
 * interface
 */
@Service
public class LandServiceImpl implements LandService {

    @Autowired
    private LandRepository landRepository;

    /**
     * Saves a Land instance
     * 
     * @param land The land object that is to be saved.
     * @return The saved land object.
     */
    public Land save(Land land) {
        return landRepository.save(land);
    }

    /**
     * It creates a new land object and saves it to the database
     * 
     * @param landDto LandDto object with the data to be saved
     * @param user    the owner of the Land entity
     * @return Saved Land
     */
    @Override
    public Land create(LandDto landDto, User user) {
        Land land = new Land();
        land.setName(landDto.getName());
        land.setUser(user);
        land.setNitrogen(landDto.getNitrogen());
        land.setPhosphorus(landDto.getPhosphorus());
        land.setPotassium(landDto.getPotassium());
        land.setTemperature(landDto.getTemperature());
        land.setHumidity(landDto.getHumidity());
        land.setPH(landDto.getPH());
        land.setRainfall(landDto.getRainfall());
        return landRepository.save(land);
    }

    /**
     * Updates the data of a Land object
     * 
     * @param landDto The LandDto object with the updated data
     * @param user    the user who owns the Land entity
     * @return Updated Land
     */
    @Override
    public Land update(LandDto landDto, User user) {
        Land land = landRepository.findById(landDto.getId()).get();
        land.setName(landDto.getName());
        land.setUser(user);
        land.setNitrogen(landDto.getNitrogen());
        land.setPhosphorus(landDto.getPhosphorus());
        land.setPotassium(landDto.getPotassium());
        land.setTemperature(landDto.getTemperature());
        land.setHumidity(landDto.getHumidity());
        land.setPH(landDto.getPH());
        land.setRainfall(landDto.getRainfall());
        return landRepository.save(land);
    }

    /**
     * Find all lands by user id and order by name.
     * 
     * @param userId The ID of the User who owns the Land entity
     * @param page   The page number to retrieve.
     * @param size   The number of entities to be returned.
     * @return The requested Land instances
     */
    @Override
    public Page<Land> findAllByUser(Long userId, int page, int size) {
        Specification<Land> specification = Specification
                .where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId));
        return landRepository.findAll(specification, PageRequest.of(page, size, Sort.by("name")));
    }

    /**
     * It deletes a land by its id
     * 
     * @param id The id of the land to be deleted
     */
    @Override
    public void delete(Land land) {
        landRepository.delete(land);
    }

    /**
     * It returns the land object with the given id
     * 
     * @param id The id of the land to be found
     * @return The land object with the id that was passed in.
     */
    @Override
    public Land findById(Long id) {
        return landRepository.findById(id).get();
    }

    @Override
    public LandDto convertToDto(Land land) {
        LandDto landDto = new LandDto();
        landDto.setId(land.getId());
        landDto.setName(land.getName());
        landDto.setNitrogen(land.getNitrogen());
        landDto.setPhosphorus(land.getPhosphorus());
        landDto.setPotassium(land.getPotassium());
        landDto.setTemperature(land.getTemperature());
        landDto.setHumidity(land.getHumidity());
        landDto.setPH(land.getPH());
        landDto.setRainfall(land.getRainfall());
        return landDto;
    }

}