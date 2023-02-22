package com.infeco.keylease.models;

import com.infeco.keylease.entity.UserEntity;
import com.infeco.keylease.entity.AuthorityEntity;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(lastName, user.lastName) && Objects.equals(firsName, user.firsName) && Objects.equals(email, user.email) && Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firsName, email, authorities);
    }
}
