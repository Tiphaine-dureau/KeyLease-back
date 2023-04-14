package com.infeco.keylease.repository;

import com.infeco.keylease.entity.RentalManagementFeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalManagementFeesRepository extends JpaRepository<RentalManagementFeesEntity, Long> {
}
