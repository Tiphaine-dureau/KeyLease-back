package com.infeco.keylease.models;

import com.infeco.keylease.entity.LeaseContractEntity;
import com.infeco.keylease.entity.OwnerEntity;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Owner extends Client {
    String iban;
    Set<UUID> leaseContractIdList;
    BigDecimal expectedRentAmount;
    BigDecimal balance;

    public Owner() {
    }

    public Owner(OwnerEntity ownerEntity) {
        super(ownerEntity);
        this.iban = ownerEntity.getIban();
        this.leaseContractIdList = ownerEntity.getLeaseContracts().stream().map(LeaseContractEntity::getId).collect(Collectors.toSet());
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Set<UUID> getLeaseContractIdList() {
        return leaseContractIdList;
    }

    public void setLeaseContractIdList(Set<UUID> leaseContractIdList) {
        this.leaseContractIdList = leaseContractIdList;
    }

    public BigDecimal getExpectedRentAmount() {
        return expectedRentAmount;
    }

    public void setExpectedRentAmount(BigDecimal expectedRentAmount) {
        this.expectedRentAmount = expectedRentAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
