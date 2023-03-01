package com.infeco.keylease;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.OwnerEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Owner;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.OwnerRepository;
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
    @InjectMocks
    OwnerService ownerService;

    @Test
    public void testGetOwners() {
        Owner owner = new Owner();
        owner.setFirstName("OwnerFirstName");
        owner.setIban("FR76 0000 6545 6789 3456");
        Address address = new Address();
        address.setStreet("Owner Street");
        owner.setAddress(address);

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName("OwnerFirstName");
        ownerEntity.setIban("FR76 0000 6545 6789 3456");
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("Owner Street");
        ownerEntity.setAddress(addressEntity);

        List<Owner> owners = List.of(owner);
        List<OwnerEntity> ownersEntity = List.of(ownerEntity);
        given(ownerRepository.findAll()).willReturn(ownersEntity);
        List<Owner> expectedOwner = ownerService.getOwners();
        assert (expectedOwner.get(0).getIban()).equals(owners.get(0).getIban());
        assert (expectedOwner.get(0).getFirstName()).equals(owners.get(0).getFirstName());
        assert (expectedOwner.get(0).getAddress().getStreet()).equals(owners.get(0).getAddress().getStreet());
        verify(ownerRepository).findAll();
    }

    @Test
    public void testAddOwner() {
        // Given
        Owner owner = new Owner();
        owner.setFirstName("FirstName");
        owner.setLastName("LastName");
        owner.setEmail("email@example.com");
        owner.setPhoneNumber("0987654321");
        owner.setIban("FR76 0000 0000 0000 0000 0000 000");

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName("FirstName");
        ownerEntity.setLastName("LastName");
        ownerEntity.setEmail("email@example.com");
        ownerEntity.setPhoneNumber("0987654321");
        ownerEntity.setIban("FR76 0000 0000 0000 0000 0000 000");

        Address address = new Address();
        owner.setAddress(address);
        address.setStreet("1 rue des Lilas");
        address.setAdditionalAddress("Bat A1 apt 11");
        address.setZipCode("33130");
        address.setTown("Bègles");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("1 rue des Lilas");
        addressEntity.setAdditionalAddress("Bat A1 apt 11");
        addressEntity.setZipCode("33130");
        addressEntity.setTown("Bègles");
        ownerEntity.setAddress(addressEntity);

        Mockito.when(ownerRepository.save(any(OwnerEntity.class))).thenReturn(ownerEntity);
        Mockito.when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        // When
        Owner savedOwner = ownerService.addOwner(owner);

        // Then
        assertNotNull(savedOwner);
        assertEquals("FirstName", savedOwner.getFirstName());
        assertEquals("LastName", savedOwner.getLastName());
        assertEquals("email@example.com", savedOwner.getEmail());
        assertEquals("0987654321", savedOwner.getPhoneNumber());
        assertEquals("FR76 0000 0000 0000 0000 0000 000", savedOwner.getIban());

        assertNotNull(savedOwner.getAddress());
        assertEquals("1 rue des Lilas", savedOwner.getAddress().getStreet());
        assertEquals("Bat A1 apt 11", savedOwner.getAddress().getAdditionalAddress());
        assertEquals("33130", savedOwner.getAddress().getZipCode());
        assertEquals("Bègles", savedOwner.getAddress().getTown());
    }

    @Test
    public void testGetOwnerById() throws NotFoundEntity {
        // Création propriétaire à factice
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
        // Exécution de la méthode à tester
        Owner ownerById = ownerService.getOwnerById(id);

        // Vérification du résultat
        assertNotNull(ownerById);
        assertEquals(id, owner.getId());
        assertEquals("John", owner.getFirstName());
        assertEquals("Doe", owner.getLastName());
        assertEquals("0787654321", owner.getPhoneNumber());
        assertEquals("doe@example.com", owner.getEmail());
        assertEquals("FR76 0001 0001 0001 0001 0001 001", owner.getIban());
        assertNotNull(owner.getAddress());
        assertEquals("1 rue des Lilas", owner.getAddress().getStreet());
        assertEquals("Bat C1 apt 22", owner.getAddress().getAdditionalAddress());
        assertEquals("33130", owner.getAddress().getZipCode());
        assertEquals("Bègles", owner.getAddress().getTown());

    }

    @Test
    public void testPutOwner() throws NotFoundEntity {
        // Création propriétaire factice
        Owner owner = new Owner();
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

        // Création d'un OwnerEntity existant
        OwnerEntity existingOwnerEntity = new OwnerEntity();
        existingOwnerEntity.setId(UUID.randomUUID());
        existingOwnerEntity.setFirstName("ExistingOwner");
        existingOwnerEntity.setAddress(new AddressEntity());

        // Simulation de la recherche de l'OwnerEntity par l'id
        when(ownerRepository.findById(existingOwnerEntity.getId())).thenReturn(Optional.of(existingOwnerEntity));

        // Simulation de la sauvegarde de l'OwnerEntity modifié
        when(ownerRepository.save(any(OwnerEntity.class))).thenAnswer(invocationOnMock -> {
            OwnerEntity savedOwnerEntity = invocationOnMock.getArgument(0);
            savedOwnerEntity.setId(existingOwnerEntity.getId());
            return savedOwnerEntity;
        });

        //Appel de la méthode à tester pour modifier un Owner existant
        Owner modifiedOwner = ownerService.modifyOwner(owner, existingOwnerEntity.getId());

        // Vérification que l'OwnerEntity a été modifié correctement
        assertEquals(owner.getFirstName(), modifiedOwner.getFirstName());
        assertEquals(owner.getAddress().getStreet(), modifiedOwner.getAddress().getStreet());
        assertEquals(owner.getAddress().getAdditionalAddress(), modifiedOwner.getAddress().getAdditionalAddress());
        assertEquals(owner.getAddress().getZipCode(), modifiedOwner.getAddress().getZipCode());
        assertEquals(owner.getAddress().getTown(), modifiedOwner.getAddress().getTown());
        // Vérification que la méthode modifyOwner lève bien une exception NotFoundEntity
        assertThrows(NotFoundEntity.class, () -> ownerService.modifyOwner(owner, UUID.randomUUID()));
        // Vérification que la méthode findById est appelée avec l'id de existingOwnerEntity
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
