package com.infeco.keylease.models;

import com.infeco.keylease.entity.RentalManagementFeesEntity;

public class RentalManagementFees {
    Long id;
    Double feeRate;

    public RentalManagementFees() {
    }

    public RentalManagementFees(RentalManagementFeesEntity rentalManagementFeesEntity) {
        this.id = rentalManagementFeesEntity.getId();
        this.feeRate = rentalManagementFeesEntity.getFeeRate();
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
