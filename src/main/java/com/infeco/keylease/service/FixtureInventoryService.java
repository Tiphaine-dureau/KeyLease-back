package com.infeco.keylease.service;

import com.infeco.keylease.entity.FixtureInventoryEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.FixtureInventory;
import com.infeco.keylease.repository.FixtureInventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FixtureInventoryService {
    private final FixtureInventoryRepository fixtureInventoryRepository;

    public FixtureInventoryService(FixtureInventoryRepository fixtureInventoryRepository) {
        this.fixtureInventoryRepository = fixtureInventoryRepository;
    }

    public List<FixtureInventory> getFixturesInventory() {
        return this.fixtureInventoryRepository.findAll().stream().map(FixtureInventory::new).collect(Collectors.toList());
    }

    public FixtureInventory getFixtureInventoryById(UUID id) throws NotFoundEntity {
        Optional<FixtureInventoryEntity> optionalFixtureInventoryEntity = this.fixtureInventoryRepository.findById(id);
        if (optionalFixtureInventoryEntity.isPresent()) {
            return entityToFixtureInventory(optionalFixtureInventoryEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    private FixtureInventory entityToFixtureInventory(FixtureInventoryEntity fixtureInventoryEntity) {
        return new FixtureInventory(fixtureInventoryEntity);
    }
}
