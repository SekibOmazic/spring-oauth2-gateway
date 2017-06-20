package com.comsysto.dcc.auth;

import com.comsysto.dcc.auth.client.Client;
import com.comsysto.dcc.auth.client.ClientRepository;
import com.comsysto.dcc.auth.role.Role;
import com.comsysto.dcc.auth.role.RoleRepository;
import com.comsysto.dcc.auth.user.User;
import com.comsysto.dcc.auth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

import static com.comsysto.dcc.auth.client.Client.DEFAULT_AUTHORIZATION_GRANT_TYPES;
import static com.comsysto.dcc.auth.client.Client.DEFAULT_SCOPE;

@Component
//@Profile(value = {"development", "test"})
public class AuthUserFixture implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private Set<Role> defaultRoles;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthUserFixture(UserRepository userRepository,
                           RoleRepository roleRepository,
                           ClientRepository clientRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();
        clientRepository.deleteAll();


        defaultRoles = Collections.singleton(roleRepository.save(new Role("USER", "User", "DemoApp")));

        createUsers();
        createClients();
    }

    private void createUsers() {
        userRepository.save(createUser("test.user@test.org", "$2a$06$xIjq.n0kw3YATtlicLM5EOfVxRxttR8TYiRV4P3.Pd50pYolfucgm"));
    }

    private User createUser(String username, String password) {
        return new User(username, password, defaultRoles);
    }


    private void createClients() {

        clientRepository.save(new Client("acme", "Acme client", "acmesecret",
                DEFAULT_AUTHORIZATION_GRANT_TYPES, DEFAULT_SCOPE, true));

        clientRepository.save(new Client("myauthserver", "Some cool client", "verysecretpassword",
                DEFAULT_AUTHORIZATION_GRANT_TYPES, DEFAULT_SCOPE, true));
    }
}
