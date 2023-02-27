package com.infeco.keylease.service;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.OwnerEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Owner;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    private final AddressRepository addressRepository;

    public OwnerService(OwnerRepository ownerRepository, AddressRepository addressRepository) {
        this.ownerRepository = ownerRepository;
        this.addressRepository = addressRepository;
    }

    public List<Owner> getOwners() {
        return this.ownerRepository.findAll().stream().map(Owner::new).collect(Collectors.toList());
    }

    public Owner addOwner(Owner owner) {
        AddressEntity addressEntity = new AddressEntity();
        addressToEntity(owner.getAddress(), addressEntity);
        AddressEntity savedAddressEntity = this.addressRepository.save(addressEntity);
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerToEntity(owner, ownerEntity);
        ownerEntity.setAddress(savedAddressEntity);
        OwnerEntity savedOwnerEntity = this.ownerRepository.save(ownerEntity);
        return entityToOwner(savedOwnerEntity);
    }

    private void ownerToEntity(Owner owner, OwnerEntity ownerEntity) {
        ownerEntity.setFirstName(owner.getFirstName());
        ownerEntity.setLastName(owner.getLastName());
        ownerEntity.setEmail(owner.getEmail());
        ownerEntity.setPhoneNumber(owner.getPhoneNumber());
        ownerEntity.setRib(owner.getRib());
    }

    private void addressToEntity(Address address, AddressEntity addressEntity) {
        addressEntity.setStreet(address.getStreet());
        addressEntity.setAdditionalAddress(address.getAdditionalAddress());
        addressEntity.setZipCode(address.getZipCode());
        addressEntity.setTown(address.getTown());
    }

    private Owner entityToOwner(OwnerEntity ownerEntity) {
        Owner owner = new Owner();
        Address address = new Address();
        owner.setId(ownerEntity.getId());
        owner.setFirstName(ownerEntity.getFirstName());
        owner.setLastName(ownerEntity.getLastName());
        owner.setEmail(ownerEntity.getEmail());
        owner.setPhoneNumber(ownerEntity.getPhoneNumber());
        owner.setRib(ownerEntity.getRib());

        address.setStreet(ownerEntity.getAddress().getStreet());
        address.setAdditionalAddress(ownerEntity.getAddress().getAdditionalAddress());
        address.setZipCode(ownerEntity.getAddress().getZipCode());
        address.setTown(ownerEntity.getAddress().getTown());
        owner.setAddress(address);

        return owner;
    }
}
