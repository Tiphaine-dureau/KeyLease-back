package com.infeco.keylease.repository;

import com.infeco.keylease.entity.LeaseContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LeaseContractRepository extends JpaRepository<LeaseContractEntity, UUID> {
}
