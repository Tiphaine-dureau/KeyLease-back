package com.infeco.keylease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rental_management_fees")
public class RentalManagementFeesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fees_seq")
    @SequenceGenerator(name = "fees_seq", sequenceName = "fees_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "fee_rate", nullable = false)
    private Double feeRate;

    public RentalManagementFeesEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(Double feeRate) {
        this.feeRate = feeRate;
    }
}
