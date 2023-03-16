package com.infeco.keylease.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "lease_contract")
public class LeaseContractEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "rent_amount", precision = 10, scale = 2)
    private BigDecimal rentAmount;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private OwnerEntity owner;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private TenantEntity tenant;

    @OneToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private PropertyEntity property;

    public LeaseContractEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public OwnerEntity getOwner() {
        return owner;
    }

    public void setOwner(OwnerEntity owner) {
        this.owner = owner;
    }

    public TenantEntity getTenant() {
        return tenant;
    }

    public void setTenant(TenantEntity tenant) {
        this.tenant = tenant;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }
}
