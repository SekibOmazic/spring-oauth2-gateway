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
import java.util.HashSet;
import java.util.Set;

import static com.comsysto.dcc.auth.client.Client.DEFAULT_AUTHORIZATION_GRANT_TYPES;
import static com.comsysto.dcc.auth.client.Client.DEFAULT_SCOPE;

@Component
//@Profile(value = {"development", "test"})
public class AuthUserFixture implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private Set<Role> defaultRoles;
    private Set<Role> adminRoles;
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


        Role userRole = roleRepository.save(new Role("USER", "User", "DemoApp"));
        defaultRoles = Collections.singleton(userRole);

        Role adminRole = roleRepository.save(new Role("ADMIN", "Admin", "DemoApp"));
        adminRoles = new HashSet<Role>() {{
            this.add(userRole);
            this.add(adminRole);
        }};

        createUsers();
        createClients();
    }

    private void createUsers() {
        userRepository.save(createUser("test.user@test.org", "$2a$06$xIjq.n0kw3YATtlicLM5EOfVxRxttR8TYiRV4P3.Pd50pYolfucgm"));
        userRepository.save(createAdminUser("admin.user@test.org", "$2a$06$xIjq.n0kw3YATtlicLM5EOfVxRxttR8TYiRV4P3.Pd50pYolfucgm"));
    }

    private User createUser(String username, String password) {
        return new User(username, password, defaultRoles);
    }

    private User createAdminUser(String username, String password) {
        return new User(username, password, adminRoles);
    }


    private void createClients() {

        clientRepository.save(
                new Client("acme",
                        "Acme client",
                        "acmesecret",
                        DEFAULT_AUTHORIZATION_GRANT_TYPES,
                        DEFAULT_SCOPE,
                        true,
                        "Simple App",
                        "http://localhost:8080/admin/")
        );

        clientRepository.save(
                new Client("myauthserver",
                        "Some cool client",
                        "verysecretpassword",
                        DEFAULT_AUTHORIZATION_GRANT_TYPES,
                        DEFAULT_SCOPE,
                        true,
                        "Test App",
                        "http://localhost:8080/admin/")
        );
    }
}
