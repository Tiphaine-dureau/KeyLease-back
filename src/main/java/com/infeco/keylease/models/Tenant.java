package com.infeco.keylease.models;

import com.infeco.keylease.entity.TenantEntity;

public class Tenant extends Client {
    String partnerLastName;
    String partnerFirstName;
    String partnerPhoneNumber;

    public Tenant() {
    }

    public Tenant(TenantEntity tenantEntity) {
        super(tenantEntity);
        this.partnerLastName = tenantEntity.getPartnerLastName();
        this.partnerFirstName = tenantEntity.getPartnerFirstName();
        this.partnerPhoneNumber = tenantEntity.getPartnerPhoneNumber();
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
}
