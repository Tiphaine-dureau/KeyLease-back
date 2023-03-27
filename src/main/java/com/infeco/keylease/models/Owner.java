package com.infeco.keylease.models;

import com.infeco.keylease.entity.OwnerEntity;

public class Owner extends Client {
    String iban;

    public Owner() {
    }

    public Owner(OwnerEntity ownerEntity) {
        super(ownerEntity);
        this.iban = ownerEntity.getIban();
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

}
