package com.infeco.keylease.service;

import com.infeco.keylease.models.Owner;
import com.infeco.keylease.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> getOwners() {
        return this.ownerRepository.findAll().stream().map(Owner::new).collect(Collectors.toList());
    }
}
