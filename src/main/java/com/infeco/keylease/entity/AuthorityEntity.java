package com.infeco.keylease.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Table(name = "kl_authority")
public class AuthorityEntity implements GrantedAuthority {

    @Id
    @NotNull
    @Size(max=50)
    @Column(length = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
       if (this == obj){
           return true;
       }
       if (!(obj instanceof AuthorityEntity)){
           return false;
       }
       return Objects.equals(name,((AuthorityEntity)obj).name);
    }

    @Override
    public String toString() {
        return "AuthorityEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
