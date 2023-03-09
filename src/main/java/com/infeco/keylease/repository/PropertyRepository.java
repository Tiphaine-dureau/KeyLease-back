package com.infeco.keylease.repository;

import com.infeco.keylease.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyRepository extends JpaRepository<PropertyEntity, UUID> {
}
