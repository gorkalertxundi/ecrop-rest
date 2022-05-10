package eus.ecrop.api.service;

import eus.ecrop.api.domain.Role;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

public interface RoleService {
    public Role save(Role role);
    public Role findByName(String name);
}