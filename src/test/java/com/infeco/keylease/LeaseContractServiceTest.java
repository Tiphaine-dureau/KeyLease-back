package com.infeco.keylease;

import com.infeco.keylease.entity.*;
import com.infeco.keylease.models.*;
import com.infeco.keylease.repository.LeaseContractRepository;
import com.infeco.keylease.service.LeaseContractService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class LeaseContractServiceTest {

    @Mock
    private LeaseContractRepository leaseContractRepository;

    @InjectMocks
    private LeaseContractService leaseContractService;

    @Test
    public void testGetLeaseContracts() {
        LeaseContract leaseContract = createLeaseContract();
        LeaseContractEntity leaseContractEntity = createLeaseContractEntity();
        List<LeaseContract> leaseContracts = List.of(leaseContract);
        List<LeaseContractEntity> leaseContractEntityList = List.of(leaseContractEntity);
        given(leaseContractRepository.findAll()).willReturn(leaseContractEntityList);

        List<LeaseContract> expectedLeaseContracts = leaseContractService.getLeaseContracts();
        assertEquals(leaseContracts.size(), expectedLeaseContracts.size());
        assertEquals(leaseContracts.get(0).getRentAmount(), expectedLeaseContracts.get(0).getRentAmount());
        assertEquals(leaseContracts.get(0).getOwner().getFirstName(), expectedLeaseContracts.get(0).getOwner().getFirstName());
        assertEquals(leaseContracts.get(0).getOwner().getIban(), expectedLeaseContracts.get(0).getOwner().getIban());
        assertEquals(leaseContracts.get(0).getOwner().getAddress().getStreet(), expectedLeaseContracts.get(0).getOwner().getAddress().getStreet());
        assertEquals(leaseContracts.get(0).getTenant().getFirstName(), expectedLeaseContracts.get(0).getTenant().getFirstName());
        assertEquals(leaseContracts.get(0).getTenant().getPartnerFirstName(), expectedLeaseContracts.get(0).getTenant().getPartnerFirstName());
        assertEquals(leaseContracts.get(0).getTenant().getAddress().getStreet(), expectedLeaseContracts.get(0).getTenant().getAddress().getStreet());
        assertEquals(leaseContracts.get(0).getProperty().getArea(), expectedLeaseContracts.get(0).getProperty().getArea());
        assertEquals(leaseContracts.get(0).getProperty().getRoomsNumber(), expectedLeaseContracts.get(0).getProperty().getRoomsNumber());
        assertEquals(leaseContracts.get(0).getProperty().getDescription(), expectedLeaseContracts.get(0).getProperty().getDescription());
        assertEquals(leaseContracts.get(0).getProperty().getType(), expectedLeaseContracts.get(0).getProperty().getType());
        assertEquals(leaseContracts.get(0).getProperty().getAddress().getStreet(), expectedLeaseContracts.get(0).getProperty().getAddress().getStreet());

        verify(leaseContractRepository).findAll();
    }

    private LeaseContract createLeaseContract() {
        LeaseContract leaseContract = new LeaseContract();
        leaseContract.setRentAmount(BigDecimal.valueOf(1250, 00));
        Owner owner = new Owner();
        owner.setFirstName("OwnerFirstName");
        owner.setLastName("OwnerLastName");
        owner.setPhoneNumber("012345678");
        owner.setEmail("owner@example.com");
        owner.setIban("FR76 0000 6545 6789 3456 345");
        owner.setAddress(createAddress());
        leaseContract.setOwner(owner);

        Tenant tenant = new Tenant();
        tenant.setFirstName("TenantFirstName");
        tenant.setLastName("TenantLastName");
        tenant.setPhoneNumber("0987654321");
        tenant.setEmail("tenant@example.com");
        tenant.setPartnerFirstName("PartnerFirstName");
        tenant.setPartnerLastName("PartnerLastName");
        tenant.setPartnerPhoneNumber("0567895656");
        tenant.setAddress(createAddress());
        leaseContract.setTenant(tenant);

        Property property = new Property();
        property.setArea("110");
        property.setRoomsNumber("5");
        property.setDescription("Maison de 5 pièces mesurant 110m2 en plein centre ville");
        property.setType("Maison");
        property.setAddress(createAddress());
        leaseContract.setProperty(property);

        return leaseContract;
    }

    private Address createAddress() {
        Address address = new Address();
        address.setStreet("1 rue des Lauriers");
        address.setZipCode("75000");
        address.setTown("Paris");

        return address;
    }

    private LeaseContractEntity createLeaseContractEntity() {
        LeaseContractEntity leaseContractEntity = new LeaseContractEntity();
        leaseContractEntity.setRentAmount(BigDecimal.valueOf(1250, 00));
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName("OwnerFirstName");
        ownerEntity.setLastName("OwnerLastName");
        ownerEntity.setPhoneNumber("0123456789");
        ownerEntity.setEmail("owner@example.com");
        ownerEntity.setIban("FR76 0000 6545 6789 3456 345");
        ownerEntity.setAddress(createAddressEntity());
        leaseContractEntity.setOwner(ownerEntity);

        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setFirstName("TenantFirstName");
        tenantEntity.setLastName("TenantLastName");
        tenantEntity.setPhoneNumber("0987654321");
        tenantEntity.setEmail("tenant@example.com");
        tenantEntity.setPartnerFirstName("PartnerFirstName");
        tenantEntity.setPartnerLastName("PartnerLastName");
        tenantEntity.setPartnerPhoneNumber("0567895656");
        tenantEntity.setAddress(createAddressEntity());
        leaseContractEntity.setTenant(tenantEntity);

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setArea("110");
        propertyEntity.setRoomsNumber("5");
        propertyEntity.setDescription("Maison de 5 pièces mesurant 110m2 en plein centre ville");
        PropertyTypeEntity propertyTypeEntity = new PropertyTypeEntity();
        propertyTypeEntity.setName("Maison");
        propertyEntity.setPropertyType(propertyTypeEntity);
        propertyEntity.setAddress(createAddressEntity());
        leaseContractEntity.setProperty(propertyEntity);

        return leaseContractEntity;
    }

    private AddressEntity createAddressEntity() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("1 rue des Lauriers");
        addressEntity.setZipCode("75000");
        addressEntity.setTown("Paris");
        
        return addressEntity;
    }
}

