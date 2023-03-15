package com.infeco.keylease.models;

import com.infeco.keylease.entity.PropertyEntity;

import java.util.UUID;

public class Property {
    UUID id;
    String area;
    String roomsNumber;
    String description;
    Address address;
    String type;

    public Property() {
    }

    public Property(PropertyEntity propertyEntity) {
        this.id = propertyEntity.getId();
        this.area = propertyEntity.getArea();
        this.roomsNumber = propertyEntity.getRoomsNumber();
        this.description = propertyEntity.getDescription();
        this.type = propertyEntity.getPropertyType().getName();
        this.address = new Address(propertyEntity.getAddress());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(String roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
