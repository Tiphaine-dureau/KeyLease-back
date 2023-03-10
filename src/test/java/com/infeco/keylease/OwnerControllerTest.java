package com.infeco.keylease;

import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Owner;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.OwnerService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class OwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OwnerService ownerService;

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetOwnersWithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetOwnersWithAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOwnersWithoutRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testAddOwner() throws Exception {
        // Cr??ation d'un propri??taire factice
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setPhoneNumber("0756432189");
        owner.setEmail("john@example.com");
        owner.setIban("FR76 0000 0000 0000 0000 0000 000");
        Address address = new Address();
        address.setStreet("1 rue des Pivoines");
        address.setAdditionalAddress("B Apt 8");
        address.setZipCode("33000");
        address.setTown("Bordeaux");
        owner.setAddress(address);
        // Mock du service pour renvoyer le propri??taire factice
        when(ownerService.addOwner(any(Owner.class))).thenReturn(owner);

        // Construction de la requ??te POST pour ajouter un propri??taire
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"0756432189\", \"email\": \"john@example.com\", \"iban\": \"FR76 0000 0000 0000 0000 0000 000\", \"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}}");

        // Ex??cution de la requ??te et v??rification du r??sultat
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"0756432189\", \"email\": \"john@example.com\", \"iban\": \"FR76 0000 0000 0000 0000 0000 000\", \"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testPutOwner() throws Exception {
        // Cr??ation d'un propri??taire factice
        Owner owner = new Owner();
        owner.setId(UUID.randomUUID());
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setPhoneNumber("0756432189");
        owner.setEmail("john@example.com");
        owner.setIban("FR76 0000 0000 0000 0000 0000 000");
        Address address = new Address();
        address.setStreet("1 rue des Pivoines");
        address.setAdditionalAddress("B Apt 8");
        address.setZipCode("33000");
        address.setTown("Bordeaux");
        owner.setAddress(address);

        // Mock du service pour renvoyer le propri??taire factice
        when(ownerService.modifyOwner(any(Owner.class), any(UUID.class))).thenReturn(owner);

        // Construction de la requ??te POST pour ajouter un propri??taire
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/owners/{id}", owner.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + owner.getId() + "\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"0756432189\", \"email\": \"john@example.com\", \"iban\": \"FR76 0000 0000 0000 0000 0000 000\", \"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}}");

        // Ex??cution de la requ??te et v??rification du r??sultat
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\": \"" + owner.getId() + "\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"0756432189\", \"email\": \"john@example.com\", \"iban\": \"FR76 0000 0000 0000 0000 0000 000\", \"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetOwnerById() throws Exception {
        // Cr??ation d'un propri??taire factice
        UUID ownerId = UUID.randomUUID();
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setPhoneNumber("0756432189");
        owner.setEmail("john@example.com");
        owner.setIban("FR76 0000 0000 0000 0000 0000 000");
        Address address = new Address();
        address.setStreet("1 rue des Pivoines");
        address.setAdditionalAddress("B Apt 8");
        address.setZipCode("33000");
        address.setTown("Bordeaux");
        owner.setAddress(address);

        // Mock du service pour renvoyer le propri??taire factice
        when(ownerService.getOwnerById(ownerId)).thenReturn(owner);

        // Construction de la requ??te GET pour r??cup??rer un propri??taire
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/owners/{id}", ownerId)
                .contentType(MediaType.APPLICATION_JSON);

        // Ex??cution de la requ??te et v??rification du r??sultat
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\": \"" + ownerId + "\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"0756432189\", \"email\": \"john@example.com\", \"iban\": \"FR76 0000 0000 0000 0000 0000 000\", \"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testDeleteOwner() throws Exception {
        // Cr??ation d'un propri??taire factice
        UUID ownerId = UUID.randomUUID();
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setPhoneNumber("0756432189");
        owner.setEmail("john@example.com");
        owner.setIban("FR76 0000 0000 0000 0000 0000 000");
        Address address = new Address();
        address.setStreet("1 rue des Pivoines");
        address.setAdditionalAddress("B Apt 8");
        address.setZipCode("33000");
        address.setTown("Bordeaux");
        owner.setAddress(address);

        // Mock du service pour renvoyer le propri??taire factice
        doNothing().when(ownerService).deleteOwner(owner.getId());

        // Construction de la requ??te DELETE pour supprimer un propri??taire
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/owners/{id}", owner.getId())
                .contentType(MediaType.APPLICATION_JSON);

        // Ex??cution de la requ??te et v??rification du r??sultat
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andReturn();

    }

}
