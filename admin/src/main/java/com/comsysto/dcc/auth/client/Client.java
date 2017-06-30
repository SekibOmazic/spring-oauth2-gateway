package com.comsysto.dcc.auth.client;

import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "clients")
public class Client {

    public static final String DEFAULT_AUTHORIZATION_GRANT_TYPES = "authorization_code,refresh_token,password";

    public static final String DEFAULT_SCOPE = "openid";

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String secret;

    @Column(nullable = false)
    private String authorizedGrantTypes;

    @Column(nullable = false)
    private String scope;

    @Column(nullable = false)
    private boolean autoApproveScopes;

    @PersistenceConstructor
    private Client() {
    }

    public Client(String id, String name, String secret, String authorizedGrantTypes, String scope, boolean autoApproveScopes) {
        this.id = id;
        this.name = name;
        this.secret = secret;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.scope = scope;
        this.autoApproveScopes = autoApproveScopes;
    }

    public Client(String id, String name, String secret) {
        this(id, name, secret, DEFAULT_AUTHORIZATION_GRANT_TYPES, DEFAULT_SCOPE, false);
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public String getScope() {
        return scope;
    }

    public boolean isAutoApproveScopes() {
        return autoApproveScopes;
    }
}
