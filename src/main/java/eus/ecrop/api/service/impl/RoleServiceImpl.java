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

@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    public Role save(Role role) {
        return roleRepository.save(role);
    }
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

}