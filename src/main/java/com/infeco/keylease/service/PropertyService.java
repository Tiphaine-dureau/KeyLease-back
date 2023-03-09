package com.infeco.keylease.service;

import com.infeco.keylease.models.Property;
import com.infeco.keylease.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getProperties() {
        return this.propertyRepository.findAll().stream().map(Property::new).collect(Collectors.toList());
    }
}
