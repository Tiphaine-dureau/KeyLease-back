package com.infeco.keylease.service;

import com.infeco.keylease.entity.*;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Owner;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.LeaseContractRepository;
import com.infeco.keylease.repository.OwnerRepository;
import com.infeco.keylease.repository.RentalManagementFeesRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final AddressRepository addressRepository;
    private final LeaseContractRepository leaseContractRepository;
    private final RentalManagementFeesRepository rentalManagementFeesRepository;

    public OwnerService(OwnerRepository ownerRepository, AddressRepository addressRepository, LeaseContractRepository leaseContractRepository, RentalManagementFeesRepository rentalManagementFeesRepository) {
        this.ownerRepository = ownerRepository;
        this.addressRepository = addressRepository;
        this.leaseContractRepository = leaseContractRepository;
        this.rentalManagementFeesRepository = rentalManagementFeesRepository;
    }

    public List<Owner> getOwners() {
        return this.ownerRepository.findAll().stream().map(Owner::new).collect(Collectors.toList());
    }

    public Owner getOwnerById(UUID id) throws NotFoundEntity {
        Optional<OwnerEntity> optionalOwnerEntity = this.ownerRepository.findById(id);
        if (optionalOwnerEntity.isPresent()) {
            return entityToOwner(optionalOwnerEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    public Owner addOwner(Owner owner) {
        AddressEntity addressEntity = new AddressEntity();
        addressToEntity(owner.getAddress(), addressEntity);
        AddressEntity savedAddressEntity = this.addressRepository.save(addressEntity);
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerToEntity(owner, ownerEntity);
        ownerEntity.setAddress(savedAddressEntity);
        OwnerEntity savedOwnerEntity = this.ownerRepository.save(ownerEntity);
        return entityToOwner(savedOwnerEntity);
    }

    public Owner modifyOwner(Owner owner, UUID id) throws NotFoundEntity {
        Optional<OwnerEntity> optionalOwnerEntity = this.ownerRepository.findById(id);
        if (optionalOwnerEntity.isPresent()) {
            OwnerEntity ownerEntity = optionalOwnerEntity.get();
            ownerToEntity(owner, ownerEntity);
            addressToEntity(owner.getAddress(), ownerEntity.getAddress());
            OwnerEntity savedEntity = this.ownerRepository.save(ownerEntity);
            return entityToOwner(savedEntity);
        } else {
            throw new NotFoundEntity();
        }
    }

    public void deleteOwner(UUID id) throws NotFoundEntity {
        Optional<OwnerEntity> optionalOwnerEntity = this.ownerRepository.findById(id);
        if (optionalOwnerEntity.isPresent()) {
            this.ownerRepository.delete(optionalOwnerEntity.get());
        } else {
            throw new NotFoundEntity();
        }
    }

    private void ownerToEntity(Owner owner, OwnerEntity ownerEntity) {
        ownerEntity.setFirstName(owner.getFirstName());
        ownerEntity.setLastName(owner.getLastName());
        ownerEntity.setBirthday(owner.getBirthday());
        ownerEntity.setEmail(owner.getEmail());
        ownerEntity.setPhoneNumber(owner.getPhoneNumber());
        ownerEntity.setIban(owner.getIban());
    }

    private void addressToEntity(Address address, AddressEntity addressEntity) {
        addressEntity.setStreet(address.getStreet());
        addressEntity.setAdditionalAddress(address.getAdditionalAddress());
        addressEntity.setZipCode(address.getZipCode());
        addressEntity.setTown(address.getTown());
    }

    private Owner entityToOwner(OwnerEntity ownerEntity) {
        Owner owner = new Owner(ownerEntity);
        setRentDataForOwner(owner, ownerEntity);
        return owner;
    }

    /**
     * Calcule le montant des loyers attendu et le solde actuel. Les frais de gestion sont soustraits
     *
     * @param owner
     * @param ownerEntity
     */
    private void setRentDataForOwner(Owner owner, OwnerEntity ownerEntity) {
        List<LeaseContractEntity> leaseContractEntityList = this.leaseContractRepository.getAllByOwnerId(ownerEntity.getId());
        RentalManagementFeesEntity rentalManagementFeesEntity = this.rentalManagementFeesRepository.findAll().get(0);
        List<PaymentEntity> paymentEntityList = new ArrayList<>();
        BigDecimal expectedRentAmount = BigDecimal.ZERO;
        Double feeRate = rentalManagementFeesEntity.getFeeRate();
        for (LeaseContractEntity leaseContractEntity : leaseContractEntityList) {
            expectedRentAmount = expectedRentAmount.add(expectedRentAmount(leaseContractEntity));
            paymentEntityList.addAll(leaseContractEntity.getPayments());
        }
        BigDecimal expectedFees = expectedRentAmount.multiply(BigDecimal.valueOf(feeRate));
        BigDecimal balance = BigDecimal.ZERO;
        for (PaymentEntity paymentEntity : paymentEntityList) {
            BigDecimal paidRent = paymentEntity.getPaidRent();
            if (paymentEntity.getAmountPaidFromCafToOwner() != null) {
                paidRent = paidRent.add(paymentEntity.getAmountPaidFromCafToOwner());
            }
            BigDecimal feesAmount = paidRent.multiply(BigDecimal.valueOf(feeRate));
            balance = balance.add(paidRent.subtract(feesAmount));
        }
        owner.setExpectedRentAmount(expectedRentAmount.subtract(expectedFees));
        owner.setBalance(balance);
    }

    private BigDecimal expectedRentAmount(LeaseContractEntity leaseContractEntity) {
        BigDecimal expectedRentAmount = BigDecimal.ZERO;
        if (leaseContractEntity.getPayments().size() > 0) {
            expectedRentAmount = leaseContractEntity.getRentAmount().multiply(BigDecimal.valueOf(leaseContractEntity.getPayments().size()));
        }
        return expectedRentAmount;
    }
}
