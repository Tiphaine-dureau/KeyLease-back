package com.infeco.keylease.controller;

import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.FixtureInventory;
import com.infeco.keylease.models.PostFixtureInventory;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.FixtureInventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/fixtures-inventory")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<FixtureInventory> addFixtureInventory(@RequestBody PostFixtureInventory fixtureInventory) {
        try {
            return ResponseEntity.ok(this.fixtureInventoryService.addFixtureInventory(fixtureInventory));
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/fixtures-inventory/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<FixtureInventory> updateFixtureInventory(@PathVariable UUID id, @RequestBody PostFixtureInventory fixtureInventory) {
        try {
            return ResponseEntity.ok(this.fixtureInventoryService.updateFixtureInventory(id, fixtureInventory));
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/fixtures-inventory/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public ResponseEntity<FixtureInventory> deleteFixtureInventory(@PathVariable UUID id) {
        try {
            fixtureInventoryService.deleteFixtureInventory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundEntity e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
