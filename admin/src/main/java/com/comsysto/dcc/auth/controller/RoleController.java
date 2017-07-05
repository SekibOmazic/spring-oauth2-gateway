package com.comsysto.dcc.auth.controller;

import com.comsysto.dcc.auth.role.Role;
import com.comsysto.dcc.auth.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/role")
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {
    
    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getUsers() {
        return roleRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Role getRole(@PathVariable String id) {
        return roleRepository.findOne(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteRole(@PathVariable String id) {
        roleRepository.delete(id);
    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public Role create(@RequestBody Role role) {
        role.setId(role.getId().toUpperCase());
        return roleRepository.save(role);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Role update(@PathVariable String id, @RequestBody Role role) {
        Role fromDB = validate(id);

        fromDB.setDisplayName(role.getDisplayName());

        return roleRepository.save(fromDB);
    }

    private Role validate(String id) {
        return this.roleRepository
                .findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

}
