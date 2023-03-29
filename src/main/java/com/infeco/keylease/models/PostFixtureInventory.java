package com.infeco.keylease.models;

import java.util.Date;
import java.util.UUID;

public class PostFixtureInventory {
    UUID propertyId;
    Date arrivalFixtureInventoryDate;
    Date exitFixtureInventoryDate;
    String arrivalComments;
    String exitComments;

    public PostFixtureInventory() {
    }

    public UUID getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(UUID propertyId) {
        this.propertyId = propertyId;
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
}
