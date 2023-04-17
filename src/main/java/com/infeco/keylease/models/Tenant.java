package com.infeco.keylease.models;

import com.infeco.keylease.entity.LeaseContractEntity;
import com.infeco.keylease.entity.TenantEntity;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Tenant extends Client {
    String partnerLastName;
    String partnerFirstName;
    String partnerPhoneNumber;
    Set<UUID> leaseContractIdList;
    BigDecimal expectedRentAmount;
    BigDecimal balance;

    public Tenant() {
    }

    public Tenant(TenantEntity tenantEntity) {
        super(tenantEntity);
        this.partnerLastName = tenantEntity.getPartnerLastName();
        this.partnerFirstName = tenantEntity.getPartnerFirstName();
        this.partnerPhoneNumber = tenantEntity.getPartnerPhoneNumber();
        this.leaseContractIdList = tenantEntity.getLeaseContracts().stream().map(LeaseContractEntity::getId).collect(Collectors.toSet());
    }

    public String getPartnerLastName() {
        return partnerLastName;
    }

    public void setPartnerLastName(String partnerLastName) {
        this.partnerLastName = partnerLastName;
    }

    public String getPartnerFirstName() {
        return partnerFirstName;
    }

    public void setPartnerFirstName(String partnerFirstName) {
        this.partnerFirstName = partnerFirstName;
    }

    public String getPartnerPhoneNumber() {
        return partnerPhoneNumber;
    }

    public void setPartnerPhoneNumber(String partnerPhoneNumber) {
        this.partnerPhoneNumber = partnerPhoneNumber;
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
