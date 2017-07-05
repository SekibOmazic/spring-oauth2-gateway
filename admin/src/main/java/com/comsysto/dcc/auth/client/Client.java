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

    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String description;

    @Column(nullable = false)
    private String callback;

    @PersistenceConstructor
    private Client() {
    }

    public Client(String id,
                  String name,
                  String secret,
                  String authorizedGrantTypes,
                  String scope,
                  boolean autoApproveScopes,
                  String description,
                  String callback) {
        this.id = id;
        this.name = name;
        this.secret = secret;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.scope = scope;
        this.autoApproveScopes = autoApproveScopes;
        this.description = description;
        this.callback = callback;
    }

    public Client(String id,
                  String name,
                  String secret,
                  String description,
                  String callback) {
        this(id, name, secret, DEFAULT_AUTHORIZATION_GRANT_TYPES, DEFAULT_SCOPE, false, description, callback);
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getScope() {
        return scope;
    }

    public boolean isAutoApproveScopes() {
        return autoApproveScopes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
