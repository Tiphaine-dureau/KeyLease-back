package com.infeco.keylease.models;

import com.infeco.keylease.entity.FixtureInventoryEntity;

import java.util.Date;
import java.util.UUID;

public class FixtureInventory {
    UUID id;
    Date arrivalFixtureInventoryDate;
    Date exitFixtureInventoryDate;
    String arrivalComments;
    String exitComments;
    Property property;

    public FixtureInventory() {
    }

    public FixtureInventory(FixtureInventoryEntity fixtureInventoryEntity) {
        this.id = fixtureInventoryEntity.getId();
        this.arrivalFixtureInventoryDate = fixtureInventoryEntity.getArrivalFixtureInventoryDate();
        this.exitFixtureInventoryDate = fixtureInventoryEntity.getExitFixtureInventoryDate();
        this.arrivalComments = fixtureInventoryEntity.getArrivalComments();
        this.exitComments = fixtureInventoryEntity.getExitComments();
        this.property = new Property(fixtureInventoryEntity.getProperty());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getArrivalFixtureInventoryDate() {
        return arrivalFixtureInventoryDate;
    }

    public void setArrivalFixtureInventoryDate(Date arrivalFixtureInventoryDate) {
        this.arrivalFixtureInventoryDate = arrivalFixtureInventoryDate;
    }

    public Date getExitFixtureInventoryDate() {
        return exitFixtureInventoryDate;
    }

    public void setExitFixtureInventoryDate(Date exitFixtureInventoryDate) {
        this.exitFixtureInventoryDate = exitFixtureInventoryDate;
    }

    public String getArrivalComments() {
        return arrivalComments;
    }

    public void setArrivalComments(String arrivalComments) {
        this.arrivalComments = arrivalComments;
    }

    public String getExitComments() {
        return exitComments;
    }

    public void setExitComments(String exitComments) {
        this.exitComments = exitComments;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
