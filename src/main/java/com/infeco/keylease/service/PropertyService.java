package com.infeco.keylease.service;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.PropertyEntity;
import com.infeco.keylease.entity.PropertyTypeEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Property;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.PropertyRepository;
import com.infeco.keylease.repository.PropertyTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final AddressRepository addressRepository;

    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyService(PropertyRepository propertyRepository, AddressRepository addressRepository, PropertyTypeRepository propertyTypeRepository) {
        this.propertyRepository = propertyRepository;
        this.addressRepository = addressRepository;
        this.propertyTypeRepository = propertyTypeRepository;
    }

    public List<Property> getProperties() {
        return this.propertyRepository.findAll().stream().map(Property::new).collect(Collectors.toList());
    }

    public Property addProperty(Property property) throws NotFoundEntity {
        AddressEntity addressEntity = new AddressEntity();
        addressToEntity(property.getAddress(), addressEntity);

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyToEntity(property, propertyEntity);

        PropertyTypeEntity propertyTypeEntity = new PropertyTypeEntity();
        Optional<PropertyTypeEntity> propertyTypeEntityOptional = this.propertyTypeRepository.findById(property.getType());
        if (propertyTypeEntityOptional.isPresent()) {
            propertyTypeEntity = propertyTypeEntityOptional.get();
        } else {
            throw new NotFoundEntity();
        }
        propertyEntity.setPropertyType(propertyTypeEntity);

        AddressEntity savedAddressEntity = this.addressRepository.save(addressEntity);
        propertyEntity.setAddress(savedAddressEntity);
        PropertyEntity savedPropertyEntity = this.propertyRepository.save(propertyEntity);
        return entityToProperty(savedPropertyEntity);
    }


    public Property getPropertyById(UUID id) throws NotFoundEntity {
        Optional<PropertyEntity> optionalPropertyEntity = this.propertyRepository.findById(id);
        if (optionalPropertyEntity.isPresent()) {
            return entityToProperty(optionalPropertyEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    public Property modifyProperty(Property property, UUID id) throws NotFoundEntity {
        Optional<PropertyEntity> optionalPropertyEntity = this.propertyRepository.findById(id);
        if (optionalPropertyEntity.isPresent()) {
            PropertyEntity propertyEntity = optionalPropertyEntity.get();
            propertyToEntity(property, propertyEntity);
            addressToEntity(property.getAddress(), propertyEntity.getAddress());
            PropertyEntity savedEntity = this.propertyRepository.save(propertyEntity);
            return entityToProperty(savedEntity);
        } else {
            throw new NotFoundEntity();
        }
    }

    private void propertyToEntity(Property property, PropertyEntity propertyEntity) {
        propertyEntity.setArea(property.getArea());
        propertyEntity.setRoomsNumber(property.getRoomsNumber());
        propertyEntity.setDescription(property.getDescription());
        PropertyTypeEntity propertyTypeEntity = new PropertyTypeEntity();
        propertyTypeEntity.setName(property.getType());
        propertyEntity.setPropertyType(propertyTypeEntity);
    }

    private void addressToEntity(Address address, AddressEntity addressEntity) {
        addressEntity.setStreet(address.getStreet());
        addressEntity.setAdditionalAddress(address.getAdditionalAddress());
        addressEntity.setZipCode(address.getZipCode());
        addressEntity.setTown(address.getTown());
    }

    private Property entityToProperty(PropertyEntity propertyEntity) {
        Property property = new Property();
        Address address = new Address();
        property.setId(propertyEntity.getId());
        property.setArea(propertyEntity.getArea());
        property.setRoomsNumber(propertyEntity.getRoomsNumber());
        property.setDescription(propertyEntity.getDescription());

        property.setType(propertyEntity.getPropertyType().getName());

        address.setStreet(propertyEntity.getAddress().getStreet());
        address.setAdditionalAddress(propertyEntity.getAddress().getAdditionalAddress());
        address.setZipCode(propertyEntity.getAddress().getZipCode());
        address.setTown(propertyEntity.getAddress().getTown());
        property.setAddress(address);

        return property;
    }
}
