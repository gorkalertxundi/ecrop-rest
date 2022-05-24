package eus.ecrop.api.service;

import eus.ecrop.api.domain.Role;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

/*
* The RoleService interface is a service interface that defines the methods that are used to manage the Role entity
*/
public interface RoleService {
    public Role save(Role role);

    public Role findByName(String name);
}