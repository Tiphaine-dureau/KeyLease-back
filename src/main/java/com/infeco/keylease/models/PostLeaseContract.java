package com.infeco.keylease.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class PostLeaseContract {
    UUID tenantId;
    UUID ownerId;
    UUID propertyId;
    BigDecimal rentAmount;
    BigDecimal rentCharges;
    BigDecimal requiredDeposit;
    BigDecimal paidDeposit;
    Date dateContractSignature;

    public PostLeaseContract() {
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(UUID propertyId) {
        this.propertyId = propertyId;
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
}
