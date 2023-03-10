package com.infeco.keylease;

import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Property;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class PropertyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetPropertiesWithUserRole() throws Exception {
        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetProperties() throws Exception {
        // Création d'un bien factice
        UUID id = UUID.randomUUID();
        Property property = new Property();
        property.setId(id);
        property.setArea("110");
        property.setRoomsNumber("5");
        property.setDescription("Maison de 5 pièces mesurant 110m2 en plein centre ville");
        property.setType("Maison");
        Address address = new Address();
        address.setStreet("1 rue des Lauriers");
        address.setZipCode("33130");
        address.setTown("Bègles");
        property.setAddress(address);

        // Mock du service de propriété
        List<Property> mockProperties = new ArrayList<>();
        mockProperties.add(property);
        when(propertyService.getProperties()).thenReturn(mockProperties);

        // Appel du endpoint de récupération des biens immobiliers
        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(id.toString())))
                .andExpect(jsonPath("$[0].area", is("110")))
                .andExpect(jsonPath("$[0].roomsNumber", is("5")))
                .andExpect(jsonPath("$[0].description", is("Maison de 5 pièces mesurant 110m2 en plein centre ville")))
                .andExpect(jsonPath("$[0].type", is("Maison")))
                .andExpect(jsonPath("$[0].address.street", is("1 rue des Lauriers")))
                .andExpect(jsonPath("$[0].address.zipCode", is("33130")))
                .andExpect(jsonPath("$[0].address.town", is("Bègles")));

        // Vérification que le service de propriété a bien été appelé
        verify(propertyService, Mockito.times(1)).getProperties();
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testAddProperty() throws Exception {
        Property property = new Property();
        property.setArea("110");
        property.setRoomsNumber("5");
        property.setDescription("Maison de 5 pièces mesurant 110m2 en plein centre ville");
        property.setType("Maison");
        Address address = new Address();
        address.setStreet("1 rue des Lauriers");
        address.setZipCode("33130");
        address.setTown("Bègles");
        property.setAddress(address);

        when(propertyService.addProperty(any(Property.class))).thenReturn(property);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"area\": \"110\", \"roomsNumber\": \"5\", \"description\": \"Maison de 5 pièces mesurant 110m2 en plein centre ville\", \"type\": \"Maison\", \"address\": {\"street\": \"1 rue des Lauriers\", \"zipCode\": \"33130\", \"town\": \"Bègles\"}}");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"area\": \"110\", \"roomsNumber\": \"5\", \"description\": \"Maison de 5 pièces mesurant 110m2 en plein centre ville\", \"type\": \"Maison\", \"address\": {\"street\": \"1 rue des Lauriers\", \"zipCode\": \"33130\", \"town\": \"Bègles\"}}"))
                .andReturn();

    }


}
