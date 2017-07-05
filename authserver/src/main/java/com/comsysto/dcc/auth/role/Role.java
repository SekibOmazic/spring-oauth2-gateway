package com.comsysto.dcc.auth.role;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    private String id;

    @Column(nullable = false)
    private String displayName;

    @Column(updatable = false)
    private String application;

    @PersistenceConstructor
    private Role() {}

    public Role(String id, String displayName, String application) {
        this.id = id;
        this.displayName = displayName;
        this.application = application;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getApplication() {
        return application;
    }

    @Override
    public String getAuthority() {
        return id;
    }
}
