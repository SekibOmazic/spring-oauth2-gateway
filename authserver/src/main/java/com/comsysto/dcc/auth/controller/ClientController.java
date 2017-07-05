package com.comsysto.dcc.auth.controller;

import com.comsysto.dcc.auth.client.Client;
import com.comsysto.dcc.auth.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.comsysto.dcc.auth.client.Client.DEFAULT_AUTHORIZATION_GRANT_TYPES;
import static com.comsysto.dcc.auth.client.Client.DEFAULT_SCOPE;

@RestController
@RequestMapping("/api/client")
@PreAuthorize("hasAuthority('ADMIN')")
public class ClientController {
    
    @Autowired
    ClientRepository clientRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Client> getUsers() {
        return clientRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Client getClient(@PathVariable String id) {
        return clientRepository.findOne(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteClient(@PathVariable String id) {
        clientRepository.delete(id);
    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public Client create(@RequestBody Client client) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String secret = passwordEncoder.encode(id);
        client.setId(id);
        client.setSecret(secret);
        client.setEnabled(true);
        client.setAuthorizedGrantTypes(DEFAULT_AUTHORIZATION_GRANT_TYPES);
        client.setScope(DEFAULT_SCOPE);

        return clientRepository.save(client);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Client update(@PathVariable String id, @RequestBody Client client) {
        Client fromDB = validateUser(id);

        fromDB.setEnabled(client.isEnabled());
        fromDB.setCallback(client.getCallback());
        fromDB.setDescription(client.getDescription());
        fromDB.setName(client.getName());

        return clientRepository.save(fromDB);
    }

    private Client validateUser(String id) {
        return this.clientRepository
                .findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
}
