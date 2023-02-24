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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
        owner.setRib("FR76 0000 6545 6789 3456");
        Address address = new Address();
        address.setStreet("Owner Street");
        owner.setAddress(address);

        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setFirstName("OwnerFirstName");
        ownerEntity.setRib("FR76 0000 6545 6789 3456");
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("Owner Street");
        ownerEntity.setAddress(addressEntity);

        List<Owner> owners = List.of(owner);
        List<OwnerEntity> ownersEntity = List.of(ownerEntity);
        given(ownerRepository.findAll()).willReturn(ownersEntity);
        List<Owner> expectedOwner = ownerService.getOwners();
        assert (expectedOwner.get(0).getRib()).equals(owners.get(0).getRib());
        assert (expectedOwner.get(0).getFirstName()).equals(owners.get(0).getFirstName());
        assert (expectedOwner.get(0).getAddress().getStreet()).equals(owners.get(0).getAddress().getStreet());
        verify(ownerRepository).findAll();
    }
}
