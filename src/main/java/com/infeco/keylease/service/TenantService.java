package com.infeco.keylease.service;

import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<Tenant> getTenants() {
        return this.tenantRepository.findAll().stream().map(Tenant::new).collect(Collectors.toList());
    }

}
