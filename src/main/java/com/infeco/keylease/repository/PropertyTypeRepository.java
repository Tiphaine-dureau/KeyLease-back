package com.infeco.keylease.repository;

import com.infeco.keylease.entity.PropertyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyTypeEntity, String> {
}
