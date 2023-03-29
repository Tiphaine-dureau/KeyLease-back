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

    // GET ALL
    public List<FixtureInventory> getFixturesInventory() {
        return this.fixtureInventoryRepository.findAll().stream().map(FixtureInventory::new).collect(Collectors.toList());
    }

    // GET BY ID
    public FixtureInventory getFixtureInventoryById(UUID id) throws NotFoundEntity {
        Optional<FixtureInventoryEntity> optionalFixtureInventoryEntity = this.fixtureInventoryRepository.findById(id);
        if (optionalFixtureInventoryEntity.isPresent()) {
            return entityToFixtureInventory(optionalFixtureInventoryEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    // POST
    public FixtureInventory addFixtureInventory(PostFixtureInventory fixtureInventory) throws NotFoundEntity {
        PropertyEntity propertyEntity = getPropertyEntityOrThrow(fixtureInventory.getPropertyId());
        FixtureInventoryEntity fixtureInventoryEntity = createFixtureInventoryEntity(fixtureInventory, propertyEntity);
        FixtureInventoryEntity savedFixtureInventoryEntity = fixtureInventoryRepository.save(fixtureInventoryEntity);
        return new FixtureInventory(savedFixtureInventoryEntity);
    }

    // PUT
    public FixtureInventory updateFixtureInventory(UUID fixtureInventoryId, PostFixtureInventory fixtureInventory) throws NotFoundEntity {
        FixtureInventoryEntity fixtureInventoryEntity = getFixtureInventoryEntityOrThrow(fixtureInventoryId);
        PropertyEntity propertyEntity = getPropertyEntityOrThrow(fixtureInventory.getPropertyId());
        fixtureInventoryEntity.setProperty(propertyEntity);
        modifyFixtureInventoryEntity(fixtureInventory, fixtureInventoryEntity);
        FixtureInventoryEntity savedFixtureInventoryEntity = fixtureInventoryRepository.save(fixtureInventoryEntity);
        return new FixtureInventory(savedFixtureInventoryEntity);
    }

    // DELETE
    public void deleteFixtureInventory(UUID id) throws NotFoundEntity {
        FixtureInventoryEntity fixtureInventoryEntity = fixtureInventoryRepository.findById(id)
                .orElseThrow(NotFoundEntity::new);
        this.fixtureInventoryRepository.delete(fixtureInventoryEntity);
    }

    // METHODS
    private PropertyEntity getPropertyEntityOrThrow(UUID propertyId) throws NotFoundEntity {
        return propertyRepository.findById(propertyId)
                .orElseThrow(NotFoundEntity::new);
    }

    private FixtureInventoryEntity getFixtureInventoryEntityOrThrow(UUID fixtureInventoryId) throws NotFoundEntity {
        return fixtureInventoryRepository.findById(fixtureInventoryId)
                .orElseThrow(NotFoundEntity::new);
    }

    private FixtureInventoryEntity createFixtureInventoryEntity(PostFixtureInventory fixtureInventory, PropertyEntity propertyEntity) {
        FixtureInventoryEntity fixtureInventoryEntity = new FixtureInventoryEntity();
        fixtureInventoryEntity.setProperty(propertyEntity);
        modifyFixtureInventoryEntity(fixtureInventory, fixtureInventoryEntity);
        return fixtureInventoryEntity;
    }

    private void modifyFixtureInventoryEntity(PostFixtureInventory fixtureInventory, FixtureInventoryEntity fixtureInventoryEntity) {
        fixtureInventoryEntity.setArrivalFixtureInventoryDate(fixtureInventory.getArrivalFixtureInventoryDate());
        fixtureInventoryEntity.setArrivalComments(fixtureInventory.getArrivalComments());
        fixtureInventoryEntity.setExitFixtureInventoryDate(fixtureInventory.getExitFixtureInventoryDate());
        fixtureInventoryEntity.setExitComments(fixtureInventory.getExitComments());
    }

    private FixtureInventory entityToFixtureInventory(FixtureInventoryEntity fixtureInventoryEntity) {
        return new FixtureInventory(fixtureInventoryEntity);
    }
}
