package com.infeco.keylease.models;

import com.infeco.keylease.entity.ClientEntity;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Client {
    UUID id;
    String lastName;
    String firstName;
    LocalDate birthday;
    String phoneNumber;
    String email;
    Address address;

    public Client() {
    }

    public Client(ClientEntity clientEntity) {
        this.id = clientEntity.getId();
        this.lastName = clientEntity.getLastName();
        this.firstName = clientEntity.getFirstName();
        this.birthday = clientEntity.getBirthday();
        this.phoneNumber = clientEntity.getPhoneNumber();
        this.address = new Address(clientEntity.getAddress());
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
