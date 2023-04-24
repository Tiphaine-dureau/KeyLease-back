package com.infeco.keylease;

import com.infeco.keylease.entity.FixtureInventoryEntity;
import com.infeco.keylease.entity.PropertyEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.FixtureInventory;
import com.infeco.keylease.models.PostFixtureInventory;
import com.infeco.keylease.repository.FixtureInventoryRepository;
import com.infeco.keylease.repository.PropertyRepository;
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

import static com.infeco.keylease.EntityUtil.createPropertyEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class FixtureInventoryServiceTest {
    @Mock
    private FixtureInventoryRepository fixtureInventoryRepository;
    @Mock
    private PropertyRepository propertyRepository;
    @InjectMocks
    private FixtureInventoryService fixtureInventoryService;

    @Test
    public void testGetFixtureInventoryById() throws NotFoundEntity, ParseException {
        FixtureInventoryEntity fixtureInventoryEntity = createFixtureInventoryEntity();

        when(fixtureInventoryRepository.findById(fixtureInventoryEntity.getId())).thenReturn(Optional.of(fixtureInventoryEntity));

        FixtureInventory fixtureInventoryById = fixtureInventoryService.getFixtureInventoryById(fixtureInventoryEntity.getId());

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

    @Test
    public void testAddFixtureInventory() throws NotFoundEntity, ParseException {
        FixtureInventoryEntity expectedFixtureInventoryEntity = createFixtureInventoryEntity();
        PostFixtureInventory postFixtureInventory = new PostFixtureInventory();
        postFixtureInventory.setPropertyId(expectedFixtureInventoryEntity.getProperty().getId());

        when(propertyRepository.findById(postFixtureInventory.getPropertyId())).thenReturn(Optional.of(expectedFixtureInventoryEntity.getProperty()));
        when(fixtureInventoryRepository.save(any(FixtureInventoryEntity.class))).thenReturn(expectedFixtureInventoryEntity);

        FixtureInventory createdFixtureInventory = fixtureInventoryService.addFixtureInventory(postFixtureInventory);

        assertEquals(expectedFixtureInventoryEntity.getProperty().getId(), createdFixtureInventory.getProperty().getId());
        assertEquals(expectedFixtureInventoryEntity.getArrivalFixtureInventoryDate(), createdFixtureInventory.getArrivalFixtureInventoryDate());
        assertEquals(expectedFixtureInventoryEntity.getArrivalComments(), createdFixtureInventory.getArrivalComments());
        assertEquals(expectedFixtureInventoryEntity.getExitFixtureInventoryDate(), createdFixtureInventory.getExitFixtureInventoryDate());
        assertEquals(expectedFixtureInventoryEntity.getExitComments(), createdFixtureInventory.getExitComments());
    }

    // METHODS
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
}
