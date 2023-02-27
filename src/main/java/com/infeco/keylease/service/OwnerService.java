package com.infeco.keylease.service;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.OwnerEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Owner;
import com.infeco.keylease.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> getOwners() {
        return this.ownerRepository.findAll().stream().map(Owner::new).collect(Collectors.toList());
    }

    public Owner addOwner(Owner owner) {
            OwnerEntity ownerEntity = new OwnerEntity();
            ownerEntity.setFirstName(owner.getFirstName());
            ownerEntity.setLastName(owner.getLastName());
            ownerEntity.setEmail(owner.getEmail());
            ownerEntity.setPhoneNumber(owner.getPhoneNumber());
            ownerEntity.setRib(owner.getRib());

            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setStreet(owner.getAddress().getStreet());
            addressEntity.setAdditionalAddress(owner.getAddress().getAdditionalAddress());
            addressEntity.setZipCode(owner.getAddress().getZipCode());
            addressEntity.setTown(owner.getAddress().getTown());
            ownerEntity.setAddress(addressEntity);

            OwnerEntity savedOwnerEntity = this.ownerRepository.save(ownerEntity);

            Owner savedOwner = new Owner();
            savedOwner.setFirstName(savedOwnerEntity.getFirstName());
            savedOwner.setLastName(savedOwnerEntity.getLastName());
            savedOwner.setEmail(savedOwnerEntity.getEmail());
            savedOwner.setPhoneNumber(savedOwnerEntity.getPhoneNumber());
            savedOwner.setRib(savedOwnerEntity.getRib());

            Address savedAddress = new Address();
            savedAddress.setStreet(savedOwnerEntity.getAddress().getStreet());
            savedAddress.setAdditionalAddress(savedOwnerEntity.getAddress().getAdditionalAddress());
            savedAddress.setZipCode(savedOwnerEntity.getAddress().getZipCode());
            savedAddress.setTown(savedOwnerEntity.getAddress().getTown());
            savedOwner.setAddress(savedAddress);

            return savedOwner;
    }
}
