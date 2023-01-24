package com.infeco.keylease.models;

import com.infeco.keylease.entity.UserEntity;
import com.infeco.keylease.entity.AuthorityEntity;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class User {
    UUID id;
    String lastName;
    String firsName;
    String email;
    Set<String> authorities;

    public User() {
    }

    public User(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.firsName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.email = userEntity.getEmail();
        this.authorities = userEntity.getAuthorities().stream().map(AuthorityEntity::getName).collect(Collectors.toSet());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
