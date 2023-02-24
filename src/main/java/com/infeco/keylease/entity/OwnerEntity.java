package com.infeco.keylease.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("O")
@Table(name = "owner")
public class OwnerEntity extends ClientEntity {

    @Column(name = "rib")
    private String rib;

    public OwnerEntity() {
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }
}
