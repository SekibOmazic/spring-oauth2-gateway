package com.comsysto.dcc.auth.repo;

import com.comsysto.dcc.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRestRepository extends JpaRepository<User, Long> {
}
