package com.infeco.keylease.service;

import com.infeco.keylease.entity.FixtureInventoryEntity;
import com.infeco.keylease.entity.PropertyEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.FixtureInventory;
import com.infeco.keylease.models.PostFixtureInventory;
import com.infeco.keylease.repository.FixtureInventoryRepository;
import com.infeco.keylease.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FixtureInventoryService {
    private final FixtureInventoryRepository fixtureInventoryRepository;
    private final PropertyRepository propertyRepository;

    public FixtureInventoryService(FixtureInventoryRepository fixtureInventoryRepository, PropertyRepository propertyRepository) {
        this.fixtureInventoryRepository = fixtureInventoryRepository;
        this.propertyRepository = propertyRepository;
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

    public FixtureInventory addFixtureInventory(PostFixtureInventory fixtureInventory) throws NotFoundEntity {
        PropertyEntity propertyEntity = propertyRepository.findById(fixtureInventory.getPropertyId())
                .orElseThrow(NotFoundEntity::new);

        FixtureInventoryEntity fixtureInventoryEntity = new FixtureInventoryEntity();
        fixtureInventoryEntity.setProperty(propertyEntity);
        fixtureInventoryEntity.setArrivalFixtureInventoryDate(fixtureInventory.getArrivalFixtureInventoryDate());
        fixtureInventoryEntity.setArrivalComments(fixtureInventory.getArrivalComments());
        fixtureInventoryEntity.setExitFixtureInventoryDate(fixtureInventory.getExitFixtureInventoryDate());
        fixtureInventoryEntity.setExitComments(fixtureInventory.getExitComments());

        FixtureInventoryEntity savedFixtureInventoryEntity = fixtureInventoryRepository.save(fixtureInventoryEntity);
        return new FixtureInventory(savedFixtureInventoryEntity);
    }

    private FixtureInventory entityToFixtureInventory(FixtureInventoryEntity fixtureInventoryEntity) {
        return new FixtureInventory(fixtureInventoryEntity);
    }
}
