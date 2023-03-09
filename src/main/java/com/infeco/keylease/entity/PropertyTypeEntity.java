package com.infeco.keylease.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "type")
public class PropertyTypeEntity {
    @Id
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PropertyTypeEntity)) {
            return false;
        }
        return Objects.equals(name, ((PropertyTypeEntity) obj).name);
    }

    @Override
    public String toString() {
        return "PropertyTypeEntity{" +
                "name='" + name + '\'' +
                '}';
    }

}
