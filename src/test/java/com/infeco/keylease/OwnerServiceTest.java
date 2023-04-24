package com.infeco.keylease;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.OwnerEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Owner;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.LeaseContractRepository;
import com.infeco.keylease.repository.OwnerRepository;
import com.infeco.keylease.repository.RentalManagementFeesRepository;
import com.infeco.keylease.service.OwnerService;
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
import java.util.UUID;

import static com.infeco.keylease.EntityUtil.createAddressEntity;
import static com.infeco.keylease.EntityUtil.createFeeListEntities;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class OwnerServiceTest {
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private LeaseContractRepository leaseContractRepository;
    @Mock
    private RentalManagementFeesRepository rentalManagementFeesRepository;
    @InjectMocks
    OwnerService ownerService;

    @Test
    public void testGetOwners() {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName("OwnerFirstName");
        ownerEntity.setLastName("LastName");
        ownerEntity.setEmail("email@example.com");
        ownerEntity.setPhoneNumber("0987654321");
        ownerEntity.setIban("FR76 0000 6545 6789 3456");
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("Owner Street");
        addressEntity.setAdditionalAddress("Owner Additional Address");
        addressEntity.setZipCode("33000");
        addressEntity.setTown("Bordeaux");
        ownerEntity.setAddress(addressEntity);

        List<OwnerEntity> ownerEntityList = List.of(ownerEntity);

        given(ownerRepository.findAll()).willReturn(ownerEntityList);
        List<Owner> ownerList = ownerService.getOwners();

        Owner firstOwner = ownerList.get(0);
        assertEquals(ownerEntity.getFirstName(), firstOwner.getFirstName());
        assertEquals(ownerEntity.getLastName(), firstOwner.getLastName());
        assertEquals(ownerEntity.getEmail(), firstOwner.getEmail());
        assertEquals(ownerEntity.getPhoneNumber(), firstOwner.getPhoneNumber());
        assertEquals(ownerEntity.getIban(), firstOwner.getIban());
        assertEquals(ownerEntity.getAddress().getStreet(), firstOwner.getAddress().getStreet());
        assertEquals(ownerEntity.getAddress().getAdditionalAddress(), firstOwner.getAddress().getAdditionalAddress());
        assertEquals(ownerEntity.getAddress().getZipCode(), firstOwner.getAddress().getZipCode());
        assertEquals(ownerEntity.getAddress().getTown(), firstOwner.getAddress().getTown());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    public void testAddOwner() {
        // Given
        Owner expectedOwner = new Owner();
        expectedOwner.setFirstName("FirstName");
        expectedOwner.setLastName("LastName");
        expectedOwner.setEmail("email@example.com");
        expectedOwner.setPhoneNumber("0987654321");
        expectedOwner.setIban("FR76 0000 0000 0000 0000 0000 000");

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName("FirstName");
        ownerEntity.setLastName("LastName");
        ownerEntity.setEmail("email@example.com");
        ownerEntity.setPhoneNumber("0987654321");
        ownerEntity.setIban("FR76 0000 0000 0000 0000 0000 000");

        Address address = new Address();
        expectedOwner.setAddress(address);
        address.setStreet("1 rue des Lauriers");
        address.setZipCode("75000");
        address.setTown("Paris");

        ownerEntity.setAddress(createAddressEntity());

        Mockito.when(ownerRepository.save(any(OwnerEntity.class))).thenReturn(ownerEntity);
        Mockito.when(addressRepository.save(any(AddressEntity.class))).thenReturn(ownerEntity.getAddress());
        Mockito.when(rentalManagementFeesRepository.findAll()).thenReturn(createFeeListEntities());

        // When
        Owner savedOwner = ownerService.addOwner(expectedOwner);

        // Then
        assertNotNull(savedOwner);
        assertEquals(expectedOwner.getFirstName(), savedOwner.getFirstName());
        assertEquals(expectedOwner.getLastName(), savedOwner.getLastName());
        assertEquals(expectedOwner.getEmail(), savedOwner.getEmail());
        assertEquals(expectedOwner.getPhoneNumber(), savedOwner.getPhoneNumber());
        assertEquals(expectedOwner.getIban(), savedOwner.getIban());

        assertNotNull(savedOwner.getAddress());
        assertEquals(expectedOwner.getAddress().getStreet(), savedOwner.getAddress().getStreet());
        assertEquals(expectedOwner.getAddress().getZipCode(), savedOwner.getAddress().getZipCode());
        assertEquals(expectedOwner.getAddress().getTown(), savedOwner.getAddress().getTown());
    }

    @Test
    public void testGetOwnerById() throws NotFoundEntity {
        // Création propriétaire à factice
        UUID ownerId = UUID.randomUUID();
        OwnerEntity expectedOwnerEntity = new OwnerEntity();
        expectedOwnerEntity.setId(ownerId);
        expectedOwnerEntity.setFirstName("John");
        expectedOwnerEntity.setLastName("Doe");
        expectedOwnerEntity.setEmail("doe@example.com");
        expectedOwnerEntity.setPhoneNumber("0787654321");
        expectedOwnerEntity.setIban("FR76 0001 0001 0001 0001 0001 001");

        expectedOwnerEntity.setAddress(createAddressEntity());

        // Mock du repository pour renvoyer le propriétaire factice
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(expectedOwnerEntity));
        Mockito.when(rentalManagementFeesRepository.findAll()).thenReturn(createFeeListEntities());
        // Exécution de la méthode à tester
        Owner ownerById = ownerService.getOwnerById(ownerId);

        // Vérification du résultat
        assertNotNull(ownerById);
        assertEquals(ownerId, ownerById.getId());
        assertEquals(expectedOwnerEntity.getFirstName(), ownerById.getFirstName());
        assertEquals(expectedOwnerEntity.getLastName(), ownerById.getLastName());
        assertEquals(expectedOwnerEntity.getPhoneNumber(), ownerById.getPhoneNumber());
        assertEquals(expectedOwnerEntity.getEmail(), ownerById.getEmail());
        assertEquals(expectedOwnerEntity.getIban(), ownerById.getIban());
        assertNotNull(expectedOwnerEntity.getAddress());
        assertEquals(expectedOwnerEntity.getAddress().getStreet(), ownerById.getAddress().getStreet());
        assertEquals(expectedOwnerEntity.getAddress().getAdditionalAddress(), ownerById.getAddress().getAdditionalAddress());
        assertEquals(expectedOwnerEntity.getAddress().getZipCode(), ownerById.getAddress().getZipCode());
        assertEquals(expectedOwnerEntity.getAddress().getTown(), ownerById.getAddress().getTown());
    }

    @Test
    public void testPutOwner() throws NotFoundEntity {
        // Création propriétaire factice
        Owner expectedOwner = new Owner();
        expectedOwner.setFirstName("John");
        expectedOwner.setLastName("Doe");
        expectedOwner.setEmail("doe@example.com");
        expectedOwner.setPhoneNumber("0787654321");
        expectedOwner.setIban("FR76 0001 0001 0001 0001 0001 001");

        Address address = new Address();
        expectedOwner.setAddress(address);
        address.setStreet("1 rue des Lilas");
        address.setAdditionalAddress("Bat C1 apt 22");
        address.setZipCode("33130");
        address.setTown("Bègles");

        // Création d'un OwnerEntity existant
        OwnerEntity existingOwnerEntity = new OwnerEntity();
        existingOwnerEntity.setId(UUID.randomUUID());
        existingOwnerEntity.setFirstName("ExistingOwner");
        existingOwnerEntity.setAddress(new AddressEntity());

        // Simulation de la recherche de l'OwnerEntity par l'id
        when(ownerRepository.findById(existingOwnerEntity.getId())).thenReturn(Optional.of(existingOwnerEntity));
        Mockito.when(rentalManagementFeesRepository.findAll()).thenReturn(createFeeListEntities());

        // Simulation de la sauvegarde de l'OwnerEntity modifié
        when(ownerRepository.save(any(OwnerEntity.class))).thenAnswer(invocationOnMock -> {
            OwnerEntity savedOwnerEntity = invocationOnMock.getArgument(0);
            savedOwnerEntity.setId(existingOwnerEntity.getId());
            return savedOwnerEntity;
        });

        //Appel de la méthode à tester pour modifier un Owner existant
        Owner modifiedOwner = ownerService.modifyOwner(expectedOwner, existingOwnerEntity.getId());

        // Vérification que l'OwnerEntity a été modifié correctement
        assertEquals(expectedOwner.getFirstName(), modifiedOwner.getFirstName());
        assertEquals(expectedOwner.getAddress().getStreet(), modifiedOwner.getAddress().getStreet());
        assertEquals(expectedOwner.getAddress().getAdditionalAddress(), modifiedOwner.getAddress().getAdditionalAddress());
        assertEquals(expectedOwner.getAddress().getZipCode(), modifiedOwner.getAddress().getZipCode());
        assertEquals(expectedOwner.getAddress().getTown(), modifiedOwner.getAddress().getTown());
        // Vérification que la méthode modifyOwner lève bien une exception NotFoundEntity
        assertThrows(NotFoundEntity.class, () -> ownerService.modifyOwner(expectedOwner, UUID.randomUUID()));
        // Vérification que la méthode findById est appelée avec l'id de l'existingOwnerEntity
        verify(ownerRepository).findById(existingOwnerEntity.getId());
        // Vérification que la méthode save est appelée avec l'OwnerEntity modifié
        verify(ownerRepository).save(any(OwnerEntity.class));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        // Création propriétaire factice
        UUID id = UUID.randomUUID();
        Owner owner = new Owner();
        owner.setId(id);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setEmail("doe@example.com");
        owner.setPhoneNumber("0787654321");
        owner.setIban("FR76 0001 0001 0001 0001 0001 001");

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName("John");
        ownerEntity.setLastName("Doe");
        ownerEntity.setEmail("doe@example.com");
        ownerEntity.setPhoneNumber("0787654321");
        ownerEntity.setIban("FR76 0001 0001 0001 0001 0001 001");

        Address address = new Address();
        owner.setAddress(address);
        address.setStreet("1 rue des Lilas");
        address.setAdditionalAddress("Bat C1 apt 22");
        address.setZipCode("33130");
        address.setTown("Bègles");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("1 rue des Lilas");
        addressEntity.setAdditionalAddress("Bat C1 apt 22");
        addressEntity.setZipCode("33130");
        addressEntity.setTown("Bègles");
        ownerEntity.setAddress(addressEntity);

        // Mock du repository pour renvoyer le propriétaire factice
        when(ownerRepository.findById(id)).thenReturn(Optional.of(ownerEntity));

        // Appel de la méthode à tester
        ownerService.deleteOwner(id);

        // Vérification que la méthode delete du repository a bien été appelée avec l'ownerEntity
        verify(ownerRepository, times(1)).delete(ownerEntity);
    }
}
