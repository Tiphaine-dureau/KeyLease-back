package com.infeco.keylease.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "fixture_inventory")
public class FixtureInventoryEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "arrival_fixture_inventory_date")
    private Date arrivalFixtureInventoryDate;
    @Column(name = "exit_fixture_inventory_date")
    private Date exitFixtureInventoryDate;
    @Column(name = "arrival_comments")
    private String arrivalComments;
    @Column(name = "exit_comments")
    private String exitComments;
    @OneToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private PropertyEntity property;

    public FixtureInventoryEntity() {
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

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }
}
