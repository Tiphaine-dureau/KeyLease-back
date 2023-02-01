package com.infeco.keylease.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class AddressEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "additional_address")
    private String additionalAddress;

    @Column(name = "zip_code", nullable = false)
    private Integer zipCode;

    @Column(name = "town", nullable = false)
    private String town;

    @OneToOne(mappedBy = "address")
    private ClientEntity client;

    public AddressEntity() {
    }

    public AddressEntity(String street, String additionalAddress, Integer zipCode, String town, ClientEntity client) {
        this.street = street;
        this.additionalAddress = additionalAddress;
        this.zipCode = zipCode;
        this.town = town;
        this.client = client;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
