package com.infeco.keylease.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("O")
@Table(name = "owner")
public class OwnerEntity extends ClientEntity {

    @Column(name = "iban")
    private String iban;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<LeaseContractEntity> leaseContracts;

    public OwnerEntity() {
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Set<LeaseContractEntity> getLeaseContracts() {
        if (leaseContracts == null) {
            leaseContracts = new HashSet<>();
        }
        return leaseContracts;
    }

    public void setLeaseContracts(Set<LeaseContractEntity> leaseContracts) {
        this.leaseContracts = leaseContracts;
    }
}
