package com.infeco.keylease.repository;

import com.infeco.keylease.entity.LeaseContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaseContractRepository extends JpaRepository<LeaseContractEntity, UUID> {
    List<LeaseContractEntity> getAllByOwnerId(UUID id);

    List<LeaseContractEntity> getAllByTenantId(UUID id);
}
