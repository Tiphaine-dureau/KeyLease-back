package com.infeco.keylease.service;

import com.infeco.keylease.entity.LeaseContractEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.LeaseContract;
import com.infeco.keylease.repository.LeaseContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LeaseContractService {
    private final LeaseContractRepository leaseContractRepository;

    public LeaseContractService(LeaseContractRepository leaseContractRepository) {
        this.leaseContractRepository = leaseContractRepository;
    }

    public List<LeaseContract> getLeaseContracts() {
        return this.leaseContractRepository.findAll().stream().map(LeaseContract::new).collect(Collectors.toList());
    }

    public LeaseContract getLeaseContractById(UUID id) throws NotFoundEntity {
        Optional<LeaseContractEntity> optionalLeaseContractEntity = this.leaseContractRepository.findById(id);
        if (optionalLeaseContractEntity.isPresent()) {
            return entityToLeaseContract(optionalLeaseContractEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    private LeaseContract entityToLeaseContract(LeaseContractEntity leaseContractEntity) {
        return new LeaseContract(leaseContractEntity);
    }
}
