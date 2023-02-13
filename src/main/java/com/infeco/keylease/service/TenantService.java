package com.infeco.keylease.service;

import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<TenantEntity> getTenants() {
        return this.tenantRepository.findAll();
    }
}
