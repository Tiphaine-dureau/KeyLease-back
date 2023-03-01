package com.infeco.keylease;

import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.TenantService;
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
public class TenantControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TenantService tenantService;

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetTenantsWithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tenants"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTenantsWithoutRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tenants"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testAddTenant() throws Exception {
        // Création d'un locataire factice
        Tenant tenant = new Tenant();
        tenant.setFirstName("John");
        tenant.setLastName("Doe");
        tenant.setPhoneNumber("0756432189");
        tenant.setEmail("john@example.com");
        tenant.setPartnerFirstName("Jane");
        tenant.setPartnerLastName("Doe");
        tenant.setPartnerPhoneNumber("0756432188");

        Address address = new Address();
        address.setStreet("1 rue des Pivoines");
        address.setAdditionalAddress("B Apt 8");
        address.setZipCode("33000");
        address.setTown("Bordeaux");
        tenant.setAddress(address);

        // Mock du service pour renvoyer le locataire factice
        when(tenantService.addTenant(any(Tenant.class))).thenReturn(tenant);
        // Construction de la requête POST pour ajouter un locataire
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/tenants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"0756432189\", " +
                        "\"email\": \"john@example.com\", " +
                        "\"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}," +
                        "\"partnerFirstName\": \"Jane\", \"partnerLastName\": \"Doe\", \"partnerPhoneNumber\": \"0756432188\"}");

        // Exécution de la requête et vérification du résultat
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"0756432189\", " +
                        "\"email\": \"john@example.com\", " +
                        "\"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}," +
                        "\"partnerFirstName\": \"Jane\", \"partnerLastName\": \"Doe\", \"partnerPhoneNumber\": \"0756432188\"}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testPutTenant() throws Exception {
        // Création d'un locataire factice
        Tenant tenant = new Tenant();
        tenant.setId(UUID.randomUUID());
        tenant.setFirstName("John");
        tenant.setLastName("Doe");
        tenant.setPhoneNumber("0756432189");
        tenant.setEmail("john@example.com");
        tenant.setPartnerFirstName("Jane");
        tenant.setPartnerLastName("Doe");
        tenant.setPartnerPhoneNumber("0756432188");

        Address address = new Address();
        address.setStreet("1 rue des Pivoines");
        address.setAdditionalAddress("B Apt 8");
        address.setZipCode("33000");
        address.setTown("Bordeaux");
        tenant.setAddress(address);

        // Mock du service pour renvoyer le locataire factice
        when(tenantService.modifyTenant(any(Tenant.class), any(UUID.class))).thenReturn(tenant);

        // Construction de la requête POST pour ajouter un locataire
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/tenants/{id}", tenant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + tenant.getId() + "\", \"firstName\": \"John\", \"lastName\": \"Doe\"," +
                        " \"phoneNumber\": \"0756432189\", \"email\": \"john@example.com\", " +
                        "\"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}," +
                        "\"partnerFirstName\": \"Jane\", \"partnerLastName\": \"Doe\", \"partnerPhoneNumber\": \"0756432188\"}");

        // Exécution de la requête et vérification du résultat
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\": \"" + tenant.getId() + "\", \"firstName\": \"John\", \"lastName\": \"Doe\", " +
                        "\"phoneNumber\": \"0756432189\", \"email\": \"john@example.com\"," +
                        " \"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}," +
                        "\"partnerFirstName\": \"Jane\", \"partnerLastName\": \"Doe\", \"partnerPhoneNumber\": \"0756432188\"}"))
                .andReturn();

    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetTenantById() throws Exception {
        // Création d'un locataire factice
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        tenant.setFirstName("John");
        tenant.setLastName("Doe");
        tenant.setPhoneNumber("0756432189");
        tenant.setEmail("john@example.com");
        tenant.setPartnerFirstName("Jane");
        tenant.setPartnerLastName("Doe");
        tenant.setPartnerPhoneNumber("0756432188");

        Address address = new Address();
        address.setStreet("1 rue des Pivoines");
        address.setAdditionalAddress("B Apt 8");
        address.setZipCode("33000");
        address.setTown("Bordeaux");
        tenant.setAddress(address);

        // Mock du service pour renvoyer le locataire factice
        when(tenantService.getTenantById(tenantId)).thenReturn(tenant);

        // Construction de la requête GET pour récupérer un locataire
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tenants/{id}", tenantId)
                .contentType(MediaType.APPLICATION_JSON);

        // Exécution de la requête et vérification du résultat
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\": \"" + tenant.getId() + "\", \"firstName\": \"John\", \"lastName\": \"Doe\", " +
                        "\"phoneNumber\": \"0756432189\", \"email\": \"john@example.com\"," +
                        " \"address\": {\"street\": \"1 rue des Pivoines\", \"additionalAddress\": \"B Apt 8\", \"zipCode\": \"33000\", \"town\": \"Bordeaux\"}," +
                        "\"partnerFirstName\": \"Jane\", \"partnerLastName\": \"Doe\", \"partnerPhoneNumber\": \"0756432188\"}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testDeleteTenant() throws Exception {
        // Création d'un locataire factice
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        tenant.setFirstName("John");
        tenant.setLastName("Doe");
        tenant.setPhoneNumber("0756432189");
        tenant.setEmail("john@example.com");
        tenant.setPartnerFirstName("Jane");
        tenant.setPartnerLastName("Doe");
        tenant.setPartnerPhoneNumber("0756432188");

        Address address = new Address();
        address.setStreet("1 rue des Pivoines");
        address.setAdditionalAddress("B Apt 8");
        address.setZipCode("33000");
        address.setTown("Bordeaux");
        tenant.setAddress(address);

        // Mock du service pour renvoyer le locataire factice
        doNothing().when(tenantService).deleteTenant(tenant.getId());

        // Construction de la requête DELETE pour supprimer un locataire
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/tenants/{id}", tenant.getId())
                .contentType(MediaType.APPLICATION_JSON);

        // Exécution de la requête et vérification du résultat
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
