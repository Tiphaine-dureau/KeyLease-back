package com.infeco.keylease;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.PropertyEntity;
import com.infeco.keylease.entity.PropertyTypeEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Property;
import com.infeco.keylease.repository.PropertyRepository;
import com.infeco.keylease.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

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
}
