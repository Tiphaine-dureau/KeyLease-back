package com.infeco.keylease.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@DiscriminatorValue("T")
@Table(name = "tenant")
public class TenantEntity extends ClientEntity {

    @Column(name = "partner_last_name")
    private String partnerLastName;

    @Column(name = "partner_first_name")
    private String partnerFirstName;

    @Column(name = "partner_phone_number")
    private String partnerPhoneNumber;

    @OneToMany(mappedBy = "tenant")
    private Set<LeaseContractEntity> leaseContracts;


    public TenantEntity() {
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

    public Set<LeaseContractEntity> getLeaseContracts() {
        return leaseContracts;
    }

    public void setLeaseContracts(Set<LeaseContractEntity> leaseContracts) {
        this.leaseContracts = leaseContracts;
    }
}
