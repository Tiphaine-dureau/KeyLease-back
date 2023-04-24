package com.infeco.keylease;

import com.infeco.keylease.entity.*;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.LeaseContract;
import com.infeco.keylease.models.PostLeaseContract;
import com.infeco.keylease.repository.LeaseContractRepository;
import com.infeco.keylease.repository.OwnerRepository;
import com.infeco.keylease.repository.PropertyRepository;
import com.infeco.keylease.repository.TenantRepository;
import com.infeco.keylease.service.LeaseContractService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.infeco.keylease.EntityUtil.createPropertyEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class LeaseContractServiceTest {
    @Mock
    private LeaseContractRepository leaseContractRepository;
    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private PropertyRepository propertyRepository;
    @InjectMocks
    private LeaseContractService leaseContractService;

    @Test
    public void testGetLeaseContracts() throws ParseException {
        LeaseContractEntity leaseContractEntity = createLeaseContractEntity();
        List<LeaseContractEntity> leaseContractEntityList = List.of(leaseContractEntity);

        given(leaseContractRepository.findAll()).willReturn(leaseContractEntityList);

        List<LeaseContract> expectedLeaseContracts = leaseContractService.getLeaseContracts();
        LeaseContractEntity firstContractEntity = leaseContractEntityList.get(0);
        LeaseContract firstExpectedContract = expectedLeaseContracts.get(0);
        assertEquals(leaseContractEntityList.size(), expectedLeaseContracts.size());

        assertEquals(firstContractEntity.getRentAmount(), firstExpectedContract.getRentAmount());
        assertEquals(firstContractEntity.getRentCharges(), firstExpectedContract.getRentCharges());
        assertEquals(firstContractEntity.getDateContractSignature(), firstExpectedContract.getDateContractSignature());
        assertEquals(firstContractEntity.getRequiredDeposit(), firstExpectedContract.getRequiredDeposit());
        assertEquals(firstContractEntity.getPaidDeposit(), firstExpectedContract.getPaidDeposit());
        assertEquals(firstContractEntity.getExpectedAmountFromCafToOwner(), firstExpectedContract.getExpectedAmountFromCafToOwner());

        assertEquals(firstContractEntity.getOwner().getFirstName(), firstExpectedContract.getOwner().getFirstName());
        assertEquals(firstContractEntity.getOwner().getLastName(), firstExpectedContract.getOwner().getLastName());
        assertEquals(firstContractEntity.getOwner().getEmail(), firstExpectedContract.getOwner().getEmail());
        assertEquals(firstContractEntity.getOwner().getPhoneNumber(), firstExpectedContract.getOwner().getPhoneNumber());
        assertEquals(firstContractEntity.getOwner().getIban(), firstExpectedContract.getOwner().getIban());
        assertEquals(firstContractEntity.getOwner().getAddress().getStreet(), firstExpectedContract.getOwner().getAddress().getStreet());
        assertEquals(firstContractEntity.getOwner().getAddress().getZipCode(), firstExpectedContract.getOwner().getAddress().getZipCode());
        assertEquals(firstContractEntity.getOwner().getAddress().getTown(), firstExpectedContract.getOwner().getAddress().getTown());

        assertEquals(firstContractEntity.getTenant().getFirstName(), firstExpectedContract.getTenant().getFirstName());
        assertEquals(firstContractEntity.getTenant().getLastName(), firstExpectedContract.getTenant().getLastName());
        assertEquals(firstContractEntity.getTenant().getEmail(), firstExpectedContract.getTenant().getEmail());
        assertEquals(firstContractEntity.getTenant().getPhoneNumber(), firstExpectedContract.getTenant().getPhoneNumber());
        assertEquals(firstContractEntity.getTenant().getPartnerFirstName(), firstExpectedContract.getTenant().getPartnerFirstName());
        assertEquals(firstContractEntity.getTenant().getPartnerLastName(), firstExpectedContract.getTenant().getPartnerLastName());
        assertEquals(firstContractEntity.getTenant().getPartnerPhoneNumber(), firstExpectedContract.getTenant().getPartnerPhoneNumber());
        assertEquals(firstContractEntity.getTenant().getAddress().getStreet(), firstExpectedContract.getTenant().getAddress().getStreet());
        assertEquals(firstContractEntity.getTenant().getAddress().getZipCode(), firstExpectedContract.getTenant().getAddress().getZipCode());
        assertEquals(firstContractEntity.getTenant().getAddress().getTown(), firstExpectedContract.getTenant().getAddress().getTown());

        assertEquals(firstContractEntity.getProperty().getArea(), firstExpectedContract.getProperty().getArea());
        assertEquals(firstContractEntity.getProperty().getRoomsNumber(), firstExpectedContract.getProperty().getRoomsNumber());
        assertEquals(firstContractEntity.getProperty().getDescription(), firstExpectedContract.getProperty().getDescription());
        assertEquals(firstContractEntity.getProperty().getPropertyType().getName(), firstExpectedContract.getProperty().getType());
        assertEquals(firstContractEntity.getProperty().getAddress().getStreet(), firstExpectedContract.getProperty().getAddress().getStreet());
        assertEquals(firstContractEntity.getProperty().getAddress().getZipCode(), firstExpectedContract.getProperty().getAddress().getZipCode());
        assertEquals(firstContractEntity.getProperty().getAddress().getTown(), firstExpectedContract.getProperty().getAddress().getTown());

        verify(leaseContractRepository).findAll();
    }

    @Test
    public void testGetLeaseContractByID() throws NotFoundEntity, ParseException {
        LeaseContractEntity leaseContractEntity = createLeaseContractEntity();
        UUID leaseContractId = UUID.randomUUID();
        leaseContractEntity.setId(leaseContractId);

        // Mock du repository pour renvoyer le contrat fictif
        when(leaseContractRepository.findById(leaseContractId)).thenReturn(Optional.of(leaseContractEntity));
        // Exécution de la méthode à tester
        LeaseContract leaseContractById = leaseContractService.getLeaseContractById(leaseContractId);

        // Vérification du résultat
        assertEquals(leaseContractId, leaseContractById.getId());
        assertEquals(leaseContractEntity.getDateContractSignature(), leaseContractById.getDateContractSignature());
        assertEquals(leaseContractEntity.getRentAmount(), leaseContractById.getRentAmount());
        assertEquals(leaseContractEntity.getRentCharges(), leaseContractById.getRentCharges());
        assertEquals(leaseContractEntity.getRequiredDeposit(), leaseContractById.getRequiredDeposit());
        assertEquals(leaseContractEntity.getPaidDeposit(), leaseContractById.getPaidDeposit());
        assertEquals(leaseContractEntity.getExpectedAmountFromCafToOwner(), leaseContractById.getExpectedAmountFromCafToOwner());

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

    @Test
    public void testAddLeaseContract() throws NotFoundEntity, ParseException {
        TenantEntity tenantEntity = createTenantEntity();
        OwnerEntity ownerEntity = createOwnerEntity();
        PropertyEntity propertyEntity = createPropertyEntity();
        LeaseContractEntity leaseContractEntity = createLeaseContractEntity();
        leaseContractEntity.getOwner().setId(ownerEntity.getId());
        leaseContractEntity.getTenant().setId(tenantEntity.getId());
        leaseContractEntity.getProperty().setId(propertyEntity.getId());
        PostLeaseContract postLeaseContract = new PostLeaseContract();
        postLeaseContract.setTenantId(tenantEntity.getId());
        postLeaseContract.setOwnerId(ownerEntity.getId());
        postLeaseContract.setPropertyId(propertyEntity.getId());

        when(tenantRepository.findById(postLeaseContract.getTenantId())).thenReturn(Optional.of(tenantEntity));
        when(ownerRepository.findById(postLeaseContract.getOwnerId())).thenReturn(Optional.of(ownerEntity));
        when(propertyRepository.findById(postLeaseContract.getPropertyId())).thenReturn(Optional.of(propertyEntity));
        when(leaseContractRepository.save(any(LeaseContractEntity.class))).thenReturn(leaseContractEntity);

        LeaseContract createdLeaseContract = leaseContractService.addLeaseContract(postLeaseContract);

        assertEquals(postLeaseContract.getOwnerId(), createdLeaseContract.getOwner().getId());
        assertEquals(postLeaseContract.getTenantId(), createdLeaseContract.getTenant().getId());
        assertEquals(postLeaseContract.getPropertyId(), createdLeaseContract.getProperty().getId());
        assertEquals(leaseContractEntity.getRentAmount(), createdLeaseContract.getRentAmount());
        assertEquals(leaseContractEntity.getRentCharges(), createdLeaseContract.getRentCharges());
        assertEquals(leaseContractEntity.getDateContractSignature(), createdLeaseContract.getDateContractSignature());
        assertEquals(leaseContractEntity.getRequiredDeposit(), createdLeaseContract.getRequiredDeposit());
        assertEquals(leaseContractEntity.getPaidDeposit(), createdLeaseContract.getPaidDeposit());
    }

    // METHODS
    private static LeaseContractEntity createLeaseContractEntity() throws ParseException {
        LeaseContractEntity leaseContractEntity = new LeaseContractEntity();
        String dateString = "2023-09-26";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateString);
        leaseContractEntity.setDateContractSignature(date);
        leaseContractEntity.setRentAmount(BigDecimal.valueOf(1250, 00));
        leaseContractEntity.setRentCharges(BigDecimal.valueOf(300, 00));
        leaseContractEntity.setRequiredDeposit(BigDecimal.valueOf(3000, 00));
        leaseContractEntity.setPaidDeposit(BigDecimal.valueOf(3000, 00));
        leaseContractEntity.setExpectedAmountFromCafToOwner(BigDecimal.valueOf(50, 00));

        OwnerEntity ownerEntity = createOwnerEntity();
        leaseContractEntity.setOwner(ownerEntity);

        TenantEntity tenantEntity = createTenantEntity();
        leaseContractEntity.setTenant(tenantEntity);

        PropertyEntity propertyEntity = createPropertyEntity();
        leaseContractEntity.setProperty(propertyEntity);

        return leaseContractEntity;
    }

    private static AddressEntity createAddressEntity() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(UUID.randomUUID());
        addressEntity.setStreet("1 rue des Lauriers");
        addressEntity.setZipCode("75000");
        addressEntity.setTown("Paris");
        return addressEntity;
    }

    private static TenantEntity createTenantEntity() {
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setId(UUID.randomUUID());
        tenantEntity.setFirstName("TenantFirstName");
        tenantEntity.setLastName("TenantLastName");
        tenantEntity.setPhoneNumber("0987654321");
        tenantEntity.setEmail("tenant@example.com");
        tenantEntity.setPartnerFirstName("PartnerFirstName");
        tenantEntity.setPartnerLastName("PartnerLastName");
        tenantEntity.setPartnerPhoneNumber("0567895656");
        tenantEntity.setAddress(createAddressEntity());
        return tenantEntity;
    }

    private static OwnerEntity createOwnerEntity() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setId(UUID.randomUUID());
        ownerEntity.setFirstName("OwnerFirstName");
        ownerEntity.setLastName("OwnerLastName");
        ownerEntity.setPhoneNumber("0123456789");
        ownerEntity.setEmail("owner@example.com");
        ownerEntity.setIban("FR76 0000 6545 6789 3456 345");
        ownerEntity.setAddress(createAddressEntity());
        return ownerEntity;
    }
}

