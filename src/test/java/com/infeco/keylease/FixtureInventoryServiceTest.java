package com.infeco.keylease;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.FixtureInventoryEntity;
import com.infeco.keylease.entity.PropertyEntity;
import com.infeco.keylease.entity.PropertyTypeEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.FixtureInventory;
import com.infeco.keylease.repository.FixtureInventoryRepository;
import com.infeco.keylease.service.FixtureInventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class FixtureInventoryServiceTest {
    @Mock
    private FixtureInventoryRepository fixtureInventoryRepository;
    @InjectMocks
    private FixtureInventoryService fixtureInventoryService;

    @Test
    public void testGetFixtureInventoryById() throws NotFoundEntity, ParseException {
        FixtureInventoryEntity fixtureInventoryEntity = createFixtureInventoryEntity();
        UUID fixtureInventoryId = UUID.randomUUID();
        fixtureInventoryEntity.setId(fixtureInventoryId);

        when(fixtureInventoryRepository.findById(fixtureInventoryId)).thenReturn(Optional.of(fixtureInventoryEntity));

        FixtureInventory fixtureInventoryById = fixtureInventoryService.getFixtureInventoryById(fixtureInventoryId);

        assertEquals(fixtureInventoryId, fixtureInventoryById.getId());
        assertEquals(fixtureInventoryEntity.getArrivalFixtureInventoryDate(), fixtureInventoryById.getArrivalFixtureInventoryDate());
        assertEquals(fixtureInventoryEntity.getArrivalComments(), fixtureInventoryById.getArrivalComments());
        assertEquals(fixtureInventoryEntity.getExitFixtureInventoryDate(), fixtureInventoryById.getExitFixtureInventoryDate());
        assertEquals(fixtureInventoryEntity.getExitComments(), fixtureInventoryById.getExitComments());

        assertEquals(fixtureInventoryEntity.getProperty().getId(), fixtureInventoryById.getProperty().getId());
        assertEquals(fixtureInventoryEntity.getProperty().getArea(), fixtureInventoryById.getProperty().getArea());
        assertEquals(fixtureInventoryEntity.getProperty().getRoomsNumber(), fixtureInventoryById.getProperty().getRoomsNumber());
        assertEquals(fixtureInventoryEntity.getProperty().getDescription(), fixtureInventoryById.getProperty().getDescription());
        assertEquals(fixtureInventoryEntity.getProperty().getPropertyType().getName(), fixtureInventoryById.getProperty().getType());
        assertEquals(fixtureInventoryEntity.getProperty().getAddress().getStreet(), fixtureInventoryById.getProperty().getAddress().getStreet());
        assertEquals(fixtureInventoryEntity.getProperty().getAddress().getZipCode(), fixtureInventoryById.getProperty().getAddress().getZipCode());
        assertEquals(fixtureInventoryEntity.getProperty().getAddress().getTown(), fixtureInventoryById.getProperty().getAddress().getTown());
    }

    private static FixtureInventoryEntity createFixtureInventoryEntity() throws ParseException {
        FixtureInventoryEntity fixtureInventoryEntity = new FixtureInventoryEntity();
        String arrivalDateString = "2023-01-01";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date arrivalDate = formatter.parse(arrivalDateString);
        fixtureInventoryEntity.setArrivalFixtureInventoryDate(arrivalDate);
        fixtureInventoryEntity.setArrivalComments("État neuf");
        String exitDateString = "2023-01-01";
        Date exitDate = formatter.parse(exitDateString);
        fixtureInventoryEntity.setExitFixtureInventoryDate(exitDate);
        fixtureInventoryEntity.setExitComments("Rien à signaler");

        PropertyEntity propertyEntity = createPropertyEntity();
        fixtureInventoryEntity.setProperty(propertyEntity);
        return fixtureInventoryEntity;
    }

    private static PropertyEntity createPropertyEntity() {
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setId(UUID.randomUUID());
        propertyEntity.setArea("110");
        propertyEntity.setRoomsNumber("5");
        propertyEntity.setDescription("Maison de 5 pièces mesurant 110m2 en plein centre ville");
        PropertyTypeEntity propertyTypeEntity = new PropertyTypeEntity();
        propertyTypeEntity.setName("Maison");
        propertyEntity.setPropertyType(propertyTypeEntity);
        propertyEntity.setAddress(createAddressEntity());
        return propertyEntity;
    }

    private static AddressEntity createAddressEntity() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(UUID.randomUUID());
        addressEntity.setStreet("1 rue des Lauriers");
        addressEntity.setZipCode("75000");
        addressEntity.setTown("Paris");
        return addressEntity;
    }
}
