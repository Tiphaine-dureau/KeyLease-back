package com.infeco.keylease.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("O")
@Table(name = "owner")
public class OwnerEntity extends ClientEntity {

    @Column(name = "iban")
    private String iban;

    public OwnerEntity() {
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

}
