package com.infeco.keylease;

import com.infeco.keylease.entity.*;
import com.infeco.keylease.exceptions.NotFoundEntity;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        LeaseContractEntity leaseContractEntity = createLeaseContractEntity();
        List<LeaseContractEntity> leaseContractEntityList = List.of(leaseContractEntity);

        given(leaseContractRepository.findAll()).willReturn(leaseContractEntityList);

        List<LeaseContract> expectedLeaseContracts = leaseContractService.getLeaseContracts();
        assertEquals(leaseContractEntityList.size(), expectedLeaseContracts.size());
        assertEquals(leaseContractEntityList.get(0).getRentAmount(), expectedLeaseContracts.get(0).getRentAmount());

        assertEquals(leaseContractEntityList.get(0).getOwner().getFirstName(), expectedLeaseContracts.get(0).getOwner().getFirstName());
        assertEquals(leaseContractEntityList.get(0).getOwner().getLastName(), expectedLeaseContracts.get(0).getOwner().getLastName());
        assertEquals(leaseContractEntityList.get(0).getOwner().getEmail(), expectedLeaseContracts.get(0).getOwner().getEmail());
        assertEquals(leaseContractEntityList.get(0).getOwner().getPhoneNumber(), expectedLeaseContracts.get(0).getOwner().getPhoneNumber());
        assertEquals(leaseContractEntityList.get(0).getOwner().getIban(), expectedLeaseContracts.get(0).getOwner().getIban());
        assertEquals(leaseContractEntityList.get(0).getOwner().getAddress().getStreet(), expectedLeaseContracts.get(0).getOwner().getAddress().getStreet());
        assertEquals(leaseContractEntityList.get(0).getOwner().getAddress().getZipCode(), expectedLeaseContracts.get(0).getOwner().getAddress().getZipCode());
        assertEquals(leaseContractEntityList.get(0).getOwner().getAddress().getTown(), expectedLeaseContracts.get(0).getOwner().getAddress().getTown());

        assertEquals(leaseContractEntityList.get(0).getTenant().getFirstName(), expectedLeaseContracts.get(0).getTenant().getFirstName());
        assertEquals(leaseContractEntityList.get(0).getTenant().getLastName(), expectedLeaseContracts.get(0).getTenant().getLastName());
        assertEquals(leaseContractEntityList.get(0).getTenant().getEmail(), expectedLeaseContracts.get(0).getTenant().getEmail());
        assertEquals(leaseContractEntityList.get(0).getTenant().getPhoneNumber(), expectedLeaseContracts.get(0).getTenant().getPhoneNumber());
        assertEquals(leaseContractEntityList.get(0).getTenant().getPartnerFirstName(), expectedLeaseContracts.get(0).getTenant().getPartnerFirstName());
        assertEquals(leaseContractEntityList.get(0).getTenant().getPartnerLastName(), expectedLeaseContracts.get(0).getTenant().getPartnerLastName());
        assertEquals(leaseContractEntityList.get(0).getTenant().getPartnerPhoneNumber(), expectedLeaseContracts.get(0).getTenant().getPartnerPhoneNumber());
        assertEquals(leaseContractEntityList.get(0).getTenant().getAddress().getStreet(), expectedLeaseContracts.get(0).getTenant().getAddress().getStreet());
        assertEquals(leaseContractEntityList.get(0).getTenant().getAddress().getZipCode(), expectedLeaseContracts.get(0).getTenant().getAddress().getZipCode());
        assertEquals(leaseContractEntityList.get(0).getTenant().getAddress().getTown(), expectedLeaseContracts.get(0).getTenant().getAddress().getTown());

        assertEquals(leaseContractEntityList.get(0).getProperty().getArea(), expectedLeaseContracts.get(0).getProperty().getArea());
        assertEquals(leaseContractEntityList.get(0).getProperty().getRoomsNumber(), expectedLeaseContracts.get(0).getProperty().getRoomsNumber());
        assertEquals(leaseContractEntityList.get(0).getProperty().getDescription(), expectedLeaseContracts.get(0).getProperty().getDescription());
        assertEquals(leaseContractEntityList.get(0).getProperty().getPropertyType().getName(), expectedLeaseContracts.get(0).getProperty().getType());
        assertEquals(leaseContractEntityList.get(0).getProperty().getAddress().getStreet(), expectedLeaseContracts.get(0).getProperty().getAddress().getStreet());
        assertEquals(leaseContractEntityList.get(0).getProperty().getAddress().getZipCode(), expectedLeaseContracts.get(0).getProperty().getAddress().getZipCode());
        assertEquals(leaseContractEntityList.get(0).getProperty().getAddress().getTown(), expectedLeaseContracts.get(0).getProperty().getAddress().getTown());

        verify(leaseContractRepository).findAll();
    }

    @Test
    public void testGetLeaseContractByID() throws NotFoundEntity {
        LeaseContractEntity leaseContractEntity = createLeaseContractEntity();
        UUID leaseContractId = UUID.randomUUID();
        leaseContractEntity.setId(leaseContractId);

        // Mock du repository pour renvoyer le contrat fictif
        when(leaseContractRepository.findById(leaseContractId)).thenReturn(Optional.of(leaseContractEntity));
        // Exécution de la méthode à tester
        LeaseContract leaseContractById = leaseContractService.getLeaseContractById(leaseContractId);

        // Vérification du résultat
        assertEquals(leaseContractId, leaseContractById.getId());
        assertEquals(leaseContractEntity.getOwner().getFirstName(), leaseContractById.getOwner().getFirstName());
        assertEquals(leaseContractEntity.getOwner().getLastName(), leaseContractById.getOwner().getLastName());
        assertEquals(leaseContractEntity.getOwner().getPhoneNumber(), leaseContractById.getOwner().getPhoneNumber());
        assertEquals(leaseContractEntity.getOwner().getEmail(), leaseContractById.getOwner().getEmail());
        assertEquals(leaseContractEntity.getOwner().getIban(), leaseContractById.getOwner().getIban());
        assertEquals(leaseContractEntity.getOwner().getAddress().getStreet(), leaseContractById.getOwner().getAddress().getStreet());
        assertEquals(leaseContractEntity.getOwner().getAddress().getZipCode(), leaseContractById.getOwner().getAddress().getZipCode());
        assertEquals(leaseContractEntity.getOwner().getAddress().getTown(), leaseContractById.getOwner().getAddress().getTown());

        assertEquals(leaseContractEntity.getTenant().getFirstName(), leaseContractById.getTenant().getFirstName());
        assertEquals(leaseContractEntity.getTenant().getLastName(), leaseContractById.getTenant().getLastName());
        assertEquals(leaseContractEntity.getTenant().getEmail(), leaseContractById.getTenant().getEmail());
        assertEquals(leaseContractEntity.getTenant().getPhoneNumber(), leaseContractById.getTenant().getPhoneNumber());
        assertEquals(leaseContractEntity.getTenant().getPartnerFirstName(), leaseContractById.getTenant().getPartnerFirstName());
        assertEquals(leaseContractEntity.getTenant().getPartnerLastName(), leaseContractById.getTenant().getPartnerLastName());
        assertEquals(leaseContractEntity.getTenant().getPartnerPhoneNumber(), leaseContractById.getTenant().getPartnerPhoneNumber());
        assertEquals(leaseContractEntity.getTenant().getAddress().getStreet(), leaseContractById.getTenant().getAddress().getStreet());
        assertEquals(leaseContractEntity.getTenant().getAddress().getZipCode(), leaseContractById.getTenant().getAddress().getZipCode());
        assertEquals(leaseContractEntity.getTenant().getAddress().getTown(), leaseContractById.getTenant().getAddress().getTown());

        assertEquals(leaseContractEntity.getProperty().getArea(), leaseContractById.getProperty().getArea());
        assertEquals(leaseContractEntity.getProperty().getRoomsNumber(), leaseContractById.getProperty().getRoomsNumber());
        assertEquals(leaseContractEntity.getProperty().getDescription(), leaseContractById.getProperty().getDescription());
        assertEquals(leaseContractEntity.getProperty().getPropertyType().getName(), leaseContractById.getProperty().getType());
        assertEquals(leaseContractEntity.getProperty().getAddress().getStreet(), leaseContractById.getProperty().getAddress().getStreet());
        assertEquals(leaseContractEntity.getProperty().getAddress().getZipCode(), leaseContractById.getProperty().getAddress().getZipCode());
        assertEquals(leaseContractEntity.getProperty().getAddress().getTown(), leaseContractById.getProperty().getAddress().getTown());

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

