package com.infeco.keylease.models;

import com.infeco.keylease.entity.PaymentEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Payment {
    UUID id;
    BigDecimal paidRent;
    Date rentPaymentDate;
    BigDecimal amountPaidFromCafToOwner;
    UUID leaseContractId;

    public Payment() {
    }

    public Payment(PaymentEntity paymentEntity) {
        this.id = paymentEntity.getId();
        this.paidRent = paymentEntity.getPaidRent();
        this.rentPaymentDate = paymentEntity.getRentPaymentDate();
        this.amountPaidFromCafToOwner = paymentEntity.getAmountPaidFromCafToOwner();
        this.leaseContractId = paymentEntity.getLeaseContract() != null ? paymentEntity.getLeaseContract().getId() : null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getPaidRent() {
        return paidRent;
    }

    public void setPaidRent(BigDecimal paidRent) {
        this.paidRent = paidRent;
    }

    public Date getRentPaymentDate() {
        return rentPaymentDate;
    }

    public void setRentPaymentDate(Date rentPaymentDate) {
        this.rentPaymentDate = rentPaymentDate;
    }

    public BigDecimal getAmountPaidFromCafToOwner() {
        return amountPaidFromCafToOwner;
    }

    public void setAmountPaidFromCafToOwner(BigDecimal amountPaidFromCafToOwner) {
        this.amountPaidFromCafToOwner = amountPaidFromCafToOwner;
    }

    public UUID getLeaseContractId() {
        return leaseContractId;
    }

    public void setLeaseContractId(UUID leaseContractId) {
        this.leaseContractId = leaseContractId;
    }
}
