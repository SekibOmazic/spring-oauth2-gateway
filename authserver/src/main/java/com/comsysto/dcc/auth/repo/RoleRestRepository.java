package com.comsysto.dcc.auth.repo;

import com.comsysto.dcc.auth.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRestRepository extends JpaRepository<Role, String> {
}