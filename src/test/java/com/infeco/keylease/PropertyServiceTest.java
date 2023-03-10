package com.infeco.keylease;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.PropertyEntity;
import com.infeco.keylease.entity.PropertyTypeEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Property;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.PropertyRepository;
import com.infeco.keylease.repository.PropertyTypeRepository;
import com.infeco.keylease.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private PropertyTypeRepository propertyTypeRepository;

    @InjectMocks
    PropertyService propertyService;

    @Test
    public void testGetProperties() {
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

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setArea("110");
        propertyEntity.setRoomsNumber("5");
        propertyEntity.setDescription("Maison de 5 pièces mesurant 110m2 en plein centre ville");
        PropertyTypeEntity propertyTypeEntity = new PropertyTypeEntity();
        propertyTypeEntity.setName("Maison");
        propertyEntity.setPropertyType(propertyTypeEntity);
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("1 rue des Lauriers");
        addressEntity.setZipCode("33130");
        addressEntity.setTown("Bègles");
        propertyEntity.setAddress(addressEntity);

        List<Property> properties = List.of(property);
        List<PropertyEntity> propertyEntityList = List.of(propertyEntity);

        // Vérification des propriétés
        given(propertyRepository.findAll()).willReturn(propertyEntityList);
        List<Property> propertyList = propertyService.getProperties();
        assertEquals(propertyList.get(0).getArea(), properties.get(0).getArea());
        assertEquals(propertyList.get(0).getRoomsNumber(), properties.get(0).getRoomsNumber());
        assertEquals(propertyList.get(0).getDescription(), properties.get(0).getDescription());
        assertEquals(propertyList.get(0).getType(), properties.get(0).getType());

        // Vérification de l'adresse
        Address expectedAddress = propertyList.get(0).getAddress();
        Address actualAddress = properties.get(0).getAddress();
        assertEquals(expectedAddress.getStreet(), actualAddress.getStreet());
        assertEquals(expectedAddress.getZipCode(), actualAddress.getZipCode());
        assertEquals(expectedAddress.getTown(), actualAddress.getTown());

        verify(propertyRepository).findAll();
    }

    @Test
    public void testAddProperty() throws Exception {
        Property property = new Property();
        property.setArea("90");
        property.setRoomsNumber("4");
        property.setDescription("Maison mitoyenne de 4 pièces mesurant 90m2 située à proximité des écoles");
        property.setType("Maison");
        Address address = new Address();
        address.setStreet("1 rue des Lilas");
        address.setZipCode("64600");
        address.setTown("Anglet");
        property.setAddress(address);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("1 rue des Lilas");
        addressEntity.setZipCode("64600");
        addressEntity.setTown("Anglet");

        PropertyTypeEntity propertyTypeEntity = new PropertyTypeEntity();
        propertyTypeEntity.setName("Maison");

        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setArea("90");
        propertyEntity.setRoomsNumber("4");
        propertyEntity.setDescription("Maison mitoyenne de 4 pièces mesurant 90m2 située à proximité des écoles");
        propertyEntity.setPropertyType(propertyTypeEntity);
        propertyEntity.setAddress(addressEntity);

        Mockito.when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
        Mockito.when(propertyTypeRepository.findById("Maison")).thenReturn(Optional.of(propertyTypeEntity));
        Mockito.when(propertyRepository.save(any(PropertyEntity.class))).thenReturn(propertyEntity);

        Property savedProperty = propertyService.addProperty(property);

        assertNotNull(savedProperty);
        assertEquals("90", savedProperty.getArea());
        assertEquals("4", savedProperty.getRoomsNumber());
        assertEquals("Maison mitoyenne de 4 pièces mesurant 90m2 située à proximité des écoles", savedProperty.getDescription());

        assertNotNull(savedProperty.getType());
        assertEquals("Maison", savedProperty.getType());

        assertNotNull(savedProperty.getAddress());
        assertEquals("1 rue des Lilas", savedProperty.getAddress().getStreet());
        assertEquals("64600", savedProperty.getAddress().getZipCode());
        assertEquals("Anglet", savedProperty.getAddress().getTown());
    }
}
