package com.infeco.keylease.service;

import com.infeco.keylease.models.LeaseContract;
import com.infeco.keylease.repository.LeaseContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
