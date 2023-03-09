package com.infeco.keylease.controller;

import com.infeco.keylease.models.Property;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.PropertyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PropertyController {
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/properties")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public List<Property> getProperties() {
        return this.propertyService.getProperties();
    }
}
