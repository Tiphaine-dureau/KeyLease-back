package com.infeco.keylease.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "property")
public class PropertyEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column
    private String area;

    @Column(name = "rooms_number")
    private String roomsNumber;

    @Column
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @JsonManagedReference
    private AddressEntity address;

    @OneToOne
    @JoinTable(
            name = "property_type",
            joinColumns = @JoinColumn(name = "property_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "type_name", referencedColumnName = "name")
    )
    private PropertyTypeEntity propertyType;

    @OneToOne(mappedBy = "property")
    private LeaseContractEntity leaseContract;

    @OneToOne(mappedBy = "property")
    private FixtureInventoryEntity fixtureInventory;

    public PropertyEntity() {
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

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public PropertyTypeEntity getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyTypeEntity propertyType) {
        this.propertyType = propertyType;
    }

    public LeaseContractEntity getLeaseContract() {
        return leaseContract;
    }

    public void setLeaseContract(LeaseContractEntity leaseContract) {
        this.leaseContract = leaseContract;
    }

    public FixtureInventoryEntity getFixtureInventory() {
        return fixtureInventory;
    }

    public void setFixtureInventory(FixtureInventoryEntity fixtureInventory) {
        this.fixtureInventory = fixtureInventory;
    }
}

