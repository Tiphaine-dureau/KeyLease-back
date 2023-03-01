package com.infeco.keylease.models;

import com.infeco.keylease.entity.AddressEntity;

public class Address {
    String street;
    String additionalAddress;
    String zipCode;
    String town;

    public Address() {
    }

    public Address(AddressEntity addressEntity) {
        this.street = addressEntity.getStreet();
        this.additionalAddress = addressEntity.getAdditionalAddress();
        this.zipCode = addressEntity.getZipCode();
        this.town = addressEntity.getTown();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalAddress() {
        return additionalAddress;
    }

    public void setAdditionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
