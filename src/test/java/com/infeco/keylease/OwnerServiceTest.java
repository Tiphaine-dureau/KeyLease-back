package com.infeco.keylease;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.OwnerEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
    public void testAddOwner(){
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
        assertEquals("FirstName",savedOwner.getFirstName());
        assertEquals("LastName",savedOwner.getLastName());
        assertEquals("email@example.com",savedOwner.getEmail());
        assertEquals("0987654321",savedOwner.getPhoneNumber());
        assertEquals("FR76 0000 0000 0000 0000 0000 000",savedOwner.getIban());

        assertNotNull(savedOwner.getAddress());
        assertEquals("1 rue des Lilas",savedOwner.getAddress().getStreet());
        assertEquals("Bat A1 apt 11",savedOwner.getAddress().getAdditionalAddress());
        assertEquals("33130",savedOwner.getAddress().getZipCode());
        assertEquals("Bègles",savedOwner.getAddress().getTown());
    }
}
