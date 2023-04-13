package com.infeco.keylease.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "paid_rent", nullable = false, precision = 10, scale = 2)
    private BigDecimal paidRent;

    @Column(name = "rent_payment_date", nullable = false)
    private Date rentPaymentDate;

    @Column(name = "amount_paid_from_caf_to_owner", precision = 10, scale = 2)
    private BigDecimal amountPaidFromCafToOwner;

    @ManyToOne
    @JoinColumn(name = "lease_contract_id", referencedColumnName = "id")
    private LeaseContractEntity leaseContract;

    public PaymentEntity() {
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

    public LeaseContractEntity getLeaseContract() {
        return leaseContract;
    }

    public void setLeaseContract(LeaseContractEntity leaseContract) {
        this.leaseContract = leaseContract;
    }
}
