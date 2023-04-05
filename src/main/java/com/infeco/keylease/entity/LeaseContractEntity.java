package com.infeco.keylease.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "rent_charges", precision = 10, scale = 2)
    private BigDecimal rentCharges;

    @Column(name = "required_deposit", precision = 10, scale = 2)
    private BigDecimal requiredDeposit;

    @Column(name = "paid_deposit", precision = 10, scale = 2)
    private BigDecimal paidDeposit;

    @Column(name = "date_contract_signature")
    private Date dateContractSignature;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private OwnerEntity owner;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private TenantEntity tenant;

    @OneToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private PropertyEntity property;

    @OneToMany(mappedBy = "leaseContract", cascade = CascadeType.REMOVE)
    private Set<PaymentEntity> payments;

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

    public BigDecimal getRentCharges() {
        return rentCharges;
    }

    public void setRentCharges(BigDecimal rentCharges) {
        this.rentCharges = rentCharges;
    }

    public BigDecimal getRequiredDeposit() {
        return requiredDeposit;
    }

    public void setRequiredDeposit(BigDecimal requiredDeposit) {
        this.requiredDeposit = requiredDeposit;
    }

    public BigDecimal getPaidDeposit() {
        return paidDeposit;
    }

    public void setPaidDeposit(BigDecimal paidDeposit) {
        this.paidDeposit = paidDeposit;
    }

    public Date getDateContractSignature() {
        return dateContractSignature;
    }

    public void setDateContractSignature(Date dateContractSignature) {
        this.dateContractSignature = dateContractSignature;
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

    public Set<PaymentEntity> getPayments() {
        if (payments == null) {
            payments = new HashSet<>();
        }
        return payments;
    }

    public void setPayments(Set<PaymentEntity> payments) {
        this.payments = payments;
    }

}
