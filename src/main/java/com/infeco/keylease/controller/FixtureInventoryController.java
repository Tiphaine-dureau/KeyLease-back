package com.infeco.keylease.controller;

import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.FixtureInventory;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.FixtureInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class FixtureInventoryController {
    private final FixtureInventoryService fixtureInventoryService;

    public FixtureInventoryController(FixtureInventoryService fixtureInventoryService) {
        this.fixtureInventoryService = fixtureInventoryService;
    }

    @GetMapping("/fixtures-inventory")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public List<FixtureInventory> getFixturesInventory() {
        return this.fixtureInventoryService.getFixturesInventory();
    }

    @GetMapping("/fixtures-inventory/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<FixtureInventory> getFixtureInventoryById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(this.fixtureInventoryService.getFixtureInventoryById(id));
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
