package com.infeco.keylease;

import com.infeco.keylease.models.*;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.LeaseContractService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class LeaseContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaseContractService leaseContractService;

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetLeaseContracts() throws Exception {
        LeaseContract leaseContract = createLeaseContract();
        List<LeaseContract> leaseContracts = List.of(leaseContract);

        when(leaseContractService.getLeaseContracts()).thenReturn(leaseContracts);
        mockMvc.perform(get("/lease-contracts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].rentAmount").value(BigDecimal.valueOf(1250, 00)))
                .andExpect(jsonPath("$[0].rentCharges").value(BigDecimal.valueOf(50, 00)))
                .andExpect(jsonPath("$[0].requiredDeposit").value(BigDecimal.valueOf(3000, 00)))
                .andExpect(jsonPath("$[0].paidDeposit").value(BigDecimal.valueOf(3000, 00)))
                .andExpect(jsonPath("$[0].expectedAmountFromCafToOwner").value(BigDecimal.valueOf(50, 00)))
                .andExpect(jsonPath("$[0].owner.firstName", is(leaseContract.getOwner().getFirstName())))
                .andExpect(jsonPath("$[0].owner.lastName", is(leaseContract.getOwner().getLastName())))
                .andExpect(jsonPath("$[0].owner.phoneNumber", is(leaseContract.getOwner().getPhoneNumber())))
                .andExpect(jsonPath("$[0].owner.email", is(leaseContract.getOwner().getEmail())))
                .andExpect(jsonPath("$[0].owner.iban", is(leaseContract.getOwner().getIban())))
                .andExpect(jsonPath("$[0].owner.address.street", is(leaseContract.getOwner().getAddress().getStreet())))
                .andExpect(jsonPath("$[0].owner.address.zipCode", is(leaseContract.getOwner().getAddress().getZipCode())))
                .andExpect(jsonPath("$[0].owner.address.town", is(leaseContract.getOwner().getAddress().getTown())))
                .andExpect(jsonPath("$[0].tenant.firstName", is(leaseContract.getTenant().getFirstName())))
                .andExpect(jsonPath("$[0].tenant.lastName", is(leaseContract.getTenant().getLastName())))
                .andExpect(jsonPath("$[0].tenant.phoneNumber", is(leaseContract.getTenant().getPhoneNumber())))
                .andExpect(jsonPath("$[0].tenant.email", is(leaseContract.getTenant().getEmail())))
                .andExpect(jsonPath("$[0].tenant.partnerFirstName", is(leaseContract.getTenant().getPartnerFirstName())))
                .andExpect(jsonPath("$[0].tenant.partnerLastName", is(leaseContract.getTenant().getPartnerLastName())))
                .andExpect(jsonPath("$[0].tenant.partnerPhoneNumber", is(leaseContract.getTenant().getPartnerPhoneNumber())))
                .andExpect(jsonPath("$[0].tenant.address.street", is(leaseContract.getTenant().getAddress().getStreet())))
                .andExpect(jsonPath("$[0].tenant.address.zipCode", is(leaseContract.getTenant().getAddress().getZipCode())))
                .andExpect(jsonPath("$[0].tenant.address.town", is(leaseContract.getTenant().getAddress().getTown())))
                .andExpect(jsonPath("$[0].property.area", is(leaseContract.getProperty().getArea())))
                .andExpect(jsonPath("$[0].property.type", is(leaseContract.getProperty().getType())))
                .andExpect(jsonPath("$[0].property.roomsNumber", is(leaseContract.getProperty().getRoomsNumber())))
                .andExpect(jsonPath("$[0].property.description", is(leaseContract.getProperty().getDescription())))
                .andExpect(jsonPath("$[0].property.address.street", is(leaseContract.getProperty().getAddress().getStreet())))
                .andExpect(jsonPath("$[0].property.address.zipCode", is(leaseContract.getProperty().getAddress().getZipCode())))
                .andExpect(jsonPath("$[0].property.address.town", is(leaseContract.getProperty().getAddress().getTown())));

    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetLeaseContractById() throws Exception {
        LeaseContract leaseContract = createLeaseContract();
        UUID leaseContractId = UUID.randomUUID();
        leaseContract.setId(leaseContractId);

        // Mock du service pour renvoyer le contrat fictif
        when(leaseContractService.getLeaseContractById(leaseContractId)).thenReturn(leaseContract);
        mockMvc.perform(get("/lease-contracts/{id}", leaseContractId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(leaseContract.getId().toString()))
                .andExpect(jsonPath("$.rentAmount").value(leaseContract.getRentAmount()))
                .andExpect(jsonPath("$.rentCharges").value(leaseContract.getRentCharges()))
                .andExpect(jsonPath("$.requiredDeposit").value(leaseContract.getRequiredDeposit()))
                .andExpect(jsonPath("$.paidDeposit").value(leaseContract.getPaidDeposit()))
                .andExpect(jsonPath("$.expectedAmountFromCafToOwner").value(leaseContract.getExpectedAmountFromCafToOwner()))
                .andExpect(jsonPath("$.owner.firstName").value(leaseContract.getOwner().getFirstName()))
                .andExpect(jsonPath("$.owner.lastName").value(leaseContract.getOwner().getLastName()))
                .andExpect(jsonPath("$.owner.phoneNumber").value(leaseContract.getOwner().getPhoneNumber()))
                .andExpect(jsonPath("$.owner.email").value(leaseContract.getOwner().getEmail()))
                .andExpect(jsonPath("$.owner.iban").value(leaseContract.getOwner().getIban()))
                .andExpect(jsonPath("$.owner.address.street").value(leaseContract.getOwner().getAddress().getStreet()))
                .andExpect(jsonPath("$.owner.address.zipCode").value(leaseContract.getOwner().getAddress().getZipCode()))
                .andExpect(jsonPath("$.owner.address.town").value(leaseContract.getOwner().getAddress().getTown()))
                .andExpect(jsonPath("$.tenant.firstName").value(leaseContract.getTenant().getFirstName()))
                .andExpect(jsonPath("$.tenant.lastName").value(leaseContract.getTenant().getLastName()))
                .andExpect(jsonPath("$.tenant.phoneNumber").value(leaseContract.getTenant().getPhoneNumber()))
                .andExpect(jsonPath("$.tenant.email").value(leaseContract.getTenant().getEmail()))
                .andExpect(jsonPath("$.tenant.partnerFirstName").value(leaseContract.getTenant().getPartnerFirstName()))
                .andExpect(jsonPath("$.tenant.partnerLastName").value(leaseContract.getTenant().getPartnerLastName()))
                .andExpect(jsonPath("$.tenant.partnerPhoneNumber").value(leaseContract.getTenant().getPartnerPhoneNumber()))
                .andExpect(jsonPath("$.tenant.address.street").value(leaseContract.getTenant().getAddress().getStreet()))
                .andExpect(jsonPath("$.tenant.address.zipCode").value(leaseContract.getTenant().getAddress().getZipCode()))
                .andExpect(jsonPath("$.tenant.address.town").value(leaseContract.getTenant().getAddress().getTown()))
                .andExpect(jsonPath("$.property.area").value(leaseContract.getProperty().getArea()))
                .andExpect(jsonPath("$.property.type").value(leaseContract.getProperty().getType()))
                .andExpect(jsonPath("$.property.roomsNumber").value(leaseContract.getProperty().getRoomsNumber()))
                .andExpect(jsonPath("$.property.description").value(leaseContract.getProperty().getDescription()))
                .andExpect(jsonPath("$.property.address.street").value(leaseContract.getProperty().getAddress().getStreet()))
                .andExpect(jsonPath("$.property.address.zipCode").value(leaseContract.getProperty().getAddress().getZipCode()))
                .andExpect(jsonPath("$.property.address.town").value(leaseContract.getProperty().getAddress().getTown()));

        verify(leaseContractService).getLeaseContractById(leaseContractId);
    }

    private LeaseContract createLeaseContract() {
        LeaseContract leaseContract = new LeaseContract();
        leaseContract.setRentAmount(BigDecimal.valueOf(1250, 00));
        leaseContract.setRentCharges(BigDecimal.valueOf(50, 00));
        leaseContract.setRequiredDeposit(BigDecimal.valueOf(3000, 00));
        leaseContract.setPaidDeposit(BigDecimal.valueOf(3000, 00));
        leaseContract.setExpectedAmountFromCafToOwner(BigDecimal.valueOf(50, 00));
        leaseContract.setExpectedAmountFromCafToOwner(BigDecimal.valueOf(50, 00));

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
}
