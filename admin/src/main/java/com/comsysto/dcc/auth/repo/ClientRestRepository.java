package com.comsysto.dcc.auth.repo;

import com.comsysto.dcc.auth.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRestRepository extends JpaRepository<Client, Long> {
}
