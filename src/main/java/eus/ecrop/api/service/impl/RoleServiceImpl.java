package eus.ecrop.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.ecrop.api.domain.Role;
import eus.ecrop.api.repository.RoleRepository;
import eus.ecrop.api.service.RoleService;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

/**
 * The RoleServiceImpl class is a service class that implements the RoleService
 * interface
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Saves a role entity
     * 
     * @param role The role object that is to be saved.
     * @return A Role object
     */
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    /**
     * If the role exists, return it, otherwise return null
     * 
     * @param name The name of the role.
     * @return A Role object.
     */
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

}