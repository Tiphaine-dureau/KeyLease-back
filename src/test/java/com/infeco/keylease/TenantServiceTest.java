package com.infeco.keylease;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.TenantRepository;
import com.infeco.keylease.service.TenantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TenantServiceTest {

    @Mock
    private TenantRepository tenantRepository;

    @InjectMocks
    TenantService tenantService;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testGetTenants() {
        Tenant tenant = new Tenant();
        tenant.setFirstName("my first name");
        Address address = new Address();
        address.setStreet("my street");
        tenant.setPartnerFirstName("PartnerFirstName");
        tenant.setAddress(address);
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setFirstName("my first name");
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("my street");
        tenantEntity.setPartnerFirstName("PartnerFirstName");
        tenantEntity.setAddress(addressEntity);
        List<Tenant> tenants = List.of(tenant);
        List<TenantEntity> tenantEntities = List.of(tenantEntity);
        given(tenantRepository.findAll()).willReturn(tenantEntities);
        List<Tenant> expected = tenantService.getTenants();
        assert (expected.get(0).getPartnerFirstName()).equals(tenants.get(0).getPartnerFirstName());
        verify(tenantRepository).findAll();
    }

    @Test
    public void testAddTenant() {
        // Given
        Tenant tenant = new Tenant();
        tenant.setFirstName("John");
        tenant.setLastName("Doe");
        tenant.setEmail("john.doe@example.com");
        tenant.setPhoneNumber("0987654321");
        tenant.setPartnerFirstName("Jane");
        tenant.setPartnerLastName("Doe");
        tenant.setPartnerPhoneNumber("1234567890");

        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setFirstName("John");
        tenantEntity.setLastName("Doe");
        tenantEntity.setEmail("john.doe@example.com");
        tenantEntity.setPhoneNumber("0987654321");
        tenantEntity.setPartnerFirstName("Jane");
        tenantEntity.setPartnerLastName("Doe");
        tenantEntity.setPartnerPhoneNumber("1234567890");
        Mockito.when(tenantRepository.save(any(TenantEntity.class))).thenReturn(tenantEntity);
        // When
        Tenant savedTenant = tenantService.addTenant(tenant);
        // Then
        assertNotNull(savedTenant);
        assertEquals("John", savedTenant.getFirstName());
        assertEquals("Doe", savedTenant.getLastName());
        assertEquals("john.doe@example.com", savedTenant.getEmail());
        assertEquals("0987654321", savedTenant.getPhoneNumber());
        assertEquals("Jane", savedTenant.getPartnerFirstName());
        assertEquals("Doe", savedTenant.getPartnerLastName());
        assertEquals("1234567890", savedTenant.getPartnerPhoneNumber());

    }
}
