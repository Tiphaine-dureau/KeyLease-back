package com.infeco.keylease.service;

import com.infeco.keylease.entity.LeaseContractEntity;
import com.infeco.keylease.entity.OwnerEntity;
import com.infeco.keylease.entity.PropertyEntity;
import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.LeaseContract;
import com.infeco.keylease.models.PostLeaseContract;
import com.infeco.keylease.repository.LeaseContractRepository;
import com.infeco.keylease.repository.OwnerRepository;
import com.infeco.keylease.repository.PropertyRepository;
import com.infeco.keylease.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LeaseContractService {
    private final LeaseContractRepository leaseContractRepository;
    private final OwnerRepository ownerRepository;
    private final TenantRepository tenantRepository;
    private final PropertyRepository propertyRepository;

    public LeaseContractService(LeaseContractRepository leaseContractRepository,
                                OwnerRepository ownerRepository,
                                TenantRepository tenantRepository,
                                PropertyRepository propertyRepository) {
        this.leaseContractRepository = leaseContractRepository;
        this.ownerRepository = ownerRepository;
        this.tenantRepository = tenantRepository;
        this.propertyRepository = propertyRepository;
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

    public LeaseContract addLeaseContract(PostLeaseContract leaseContract) throws NotFoundEntity {
        OwnerEntity ownerEntity = ownerRepository.findById(leaseContract.getOwnerId())
                .orElseThrow(NotFoundEntity::new);
        TenantEntity tenantEntity = tenantRepository.findById(leaseContract.getTenantId())
                .orElseThrow(NotFoundEntity::new);
        PropertyEntity propertyEntity = propertyRepository.findById(leaseContract.getPropertyId())
                .orElseThrow(NotFoundEntity::new);

        LeaseContractEntity leaseContractEntity = new LeaseContractEntity();
        leaseContractEntity.setOwner(ownerEntity);
        leaseContractEntity.setTenant(tenantEntity);
        leaseContractEntity.setProperty(propertyEntity);
        leaseContractEntity.setRentAmount(leaseContract.getRentAmount());
        leaseContractEntity.setRentCharges(leaseContract.getRentCharges());
        leaseContractEntity.setDateContractSignature(leaseContract.getDateContractSignature());

        LeaseContractEntity savedLeaseContractEntity = leaseContractRepository.save(leaseContractEntity);
        return new LeaseContract(savedLeaseContractEntity);
    }

    private LeaseContract entityToLeaseContract(LeaseContractEntity leaseContractEntity) {
        return new LeaseContract(leaseContractEntity);
    }
}
