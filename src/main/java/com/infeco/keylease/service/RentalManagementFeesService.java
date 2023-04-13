package com.infeco.keylease.service;

import com.infeco.keylease.entity.RentalManagementFeesEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.RentalManagementFees;
import com.infeco.keylease.repository.RentalManagementFeesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentalManagementFeesService {
    private final RentalManagementFeesRepository rentalManagementFeesRepository;

    public RentalManagementFeesService(RentalManagementFeesRepository rentalManagementFeesRepository) {
        this.rentalManagementFeesRepository = rentalManagementFeesRepository;
    }

    // GET BY ID
    public RentalManagementFees getRentalManagementFees(Long id) throws NotFoundEntity {
        Optional<RentalManagementFeesEntity> optionalRentalManagementFeesEntity = this.rentalManagementFeesRepository.findById(id);
        if (optionalRentalManagementFeesEntity.isPresent()) {
            return entityToRentalManagementFees(optionalRentalManagementFeesEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    // METHODS
    private RentalManagementFees entityToRentalManagementFees(RentalManagementFeesEntity rentalManagementFeesEntity) {
        return new RentalManagementFees(rentalManagementFeesEntity);
    }
}
