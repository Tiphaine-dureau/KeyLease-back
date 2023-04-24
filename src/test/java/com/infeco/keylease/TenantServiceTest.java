package com.infeco.keylease;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.exceptions.NotFoundEntity;
import com.infeco.keylease.models.Address;
import com.infeco.keylease.models.Tenant;
import com.infeco.keylease.repository.AddressRepository;
import com.infeco.keylease.repository.LeaseContractRepository;
import com.infeco.keylease.repository.TenantRepository;
import com.infeco.keylease.service.TenantService;
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
public class TenantServiceTest {

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private LeaseContractRepository leaseContractRepository;

    @InjectMocks
    TenantService tenantService;

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

        Address address = new Address();
        tenant.setAddress(address);
        address.setStreet("1 rue des mimosas");
        address.setAdditionalAddress("Bâtiment 1 apt 22");
        address.setZipCode("33000");
        address.setTown("Bordeaux");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("1 rue des mimosas");
        addressEntity.setAdditionalAddress("Bâtiment 1 apt 22");
        addressEntity.setZipCode("33000");
        addressEntity.setTown("Bordeaux");
        tenantEntity.setAddress(addressEntity);

        Mockito.when(tenantRepository.save(any(TenantEntity.class))).thenReturn(tenantEntity);
        Mockito.when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

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

        assertNotNull(savedTenant.getAddress());
        assertEquals("1 rue des mimosas", savedTenant.getAddress().getStreet());
        assertEquals("Bâtiment 1 apt 22", savedTenant.getAddress().getAdditionalAddress());
        assertEquals("33000", savedTenant.getAddress().getZipCode());
        assertEquals("Bordeaux", savedTenant.getAddress().getTown());
    }

    @Test
    public void testGetTenantById() throws NotFoundEntity {
        UUID id = UUID.randomUUID();
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setFirstName("John");
        tenant.setLastName("Doe");
        tenant.setEmail("doe@example.com");
        tenant.setPhoneNumber("0787654321");
        tenant.setPartnerFirstName("Jane");
        tenant.setPartnerLastName("Doe");
        tenant.setPartnerPhoneNumber("0787654322");

        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setFirstName("John");
        tenantEntity.setLastName("Doe");
        tenantEntity.setEmail("doe@example.com");
        tenantEntity.setPhoneNumber("0787654321");
        tenantEntity.setPartnerFirstName("Jane");
        tenantEntity.setPartnerLastName("Doe");
        tenantEntity.setPartnerPhoneNumber("0787654322");

        Address address = new Address();
        tenant.setAddress(address);
        address.setStreet("1 rue des Lilas");
        address.setAdditionalAddress("Bat C1 apt 22");
        address.setZipCode("33130");
        address.setTown("Bègles");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("1 rue des Lilas");
        addressEntity.setAdditionalAddress("Bat C1 apt 22");
        addressEntity.setZipCode("33130");
        addressEntity.setTown("Bègles");
        tenantEntity.setAddress(addressEntity);

        // Mock du repository pour renvoyer le propriétaire fictif
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenantEntity));
        // Exécution de la méthode à tester
        Tenant tenantById = tenantService.getTenantById(id);

        // Vérification du résultat
        assertNotNull(tenantById);
        assertEquals(id, tenant.getId());
        assertEquals("John", tenant.getFirstName());
        assertEquals("Doe", tenant.getLastName());
        assertEquals("0787654321", tenant.getPhoneNumber());
        assertEquals("doe@example.com", tenant.getEmail());
        assertEquals("Jane", tenant.getPartnerFirstName());
        assertEquals("Doe", tenant.getPartnerLastName());
        assertNotNull(tenant.getAddress());
        assertEquals("1 rue des Lilas", tenant.getAddress().getStreet());
        assertEquals("Bat C1 apt 22", tenant.getAddress().getAdditionalAddress());
        assertEquals("33130", tenant.getAddress().getZipCode());
        assertEquals("Bègles", tenant.getAddress().getTown());
    }

    @Test
    public void testPutTenant() throws NotFoundEntity {
        // Création d'un locataire factice
        Tenant tenant = new Tenant();
        tenant.setFirstName("John");
        tenant.setLastName("Doe");
        tenant.setEmail("doe@example.com");
        tenant.setPhoneNumber("0787654321");
        tenant.setPartnerFirstName("Jane");
        tenant.setPartnerLastName("Doe");
        tenant.setPartnerPhoneNumber("0787654322");

        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setFirstName("John");
        tenantEntity.setLastName("Doe");
        tenantEntity.setEmail("doe@example.com");
        tenantEntity.setPhoneNumber("0787654321");
        tenantEntity.setPartnerFirstName("Jane");
        tenantEntity.setPartnerLastName("Doe");
        tenantEntity.setPartnerPhoneNumber("0787654322");

        Address address = new Address();
        tenant.setAddress(address);
        address.setStreet("1 rue des Lilas");
        address.setAdditionalAddress("Bat C1 apt 22");
        address.setZipCode("33130");
        address.setTown("Bègles");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("1 rue des Lilas");
        addressEntity.setAdditionalAddress("Bat C1 apt 22");
        addressEntity.setZipCode("33130");
        addressEntity.setTown("Bègles");
        tenantEntity.setAddress(addressEntity);

        // Création d'un TenantEntity existant
        TenantEntity existingTenantEntity = new TenantEntity();
        existingTenantEntity.setId(UUID.randomUUID());
        existingTenantEntity.setPartnerFirstName("ExistingTenant");
        existingTenantEntity.setAddress(new AddressEntity());

        // Simulation de la recherche du TenantEntity modifié
        when(tenantRepository.findById(existingTenantEntity.getId())).thenReturn(Optional.of(existingTenantEntity));

        // Simulation de la sauvegarde du TenantEntity modifié
        when(tenantRepository.save(any(TenantEntity.class))).thenAnswer(invocationOnMock -> {
            TenantEntity savedTenantEntity = invocationOnMock.getArgument(0);
            savedTenantEntity.setId(existingTenantEntity.getId());
            return savedTenantEntity;
        });

        // Appel de la méthode à tester pour modifier un Tenant existant
        Tenant modifiedTenant = tenantService.modifyTenant(tenant, existingTenantEntity.getId());

        // Vérification que TenantEntity a été modifié correctement
        assertEquals(tenant.getFirstName(), modifiedTenant.getFirstName());
        assertEquals(tenant.getAddress().getStreet(), modifiedTenant.getAddress().getStreet());
        assertEquals(tenant.getAddress().getAdditionalAddress(), modifiedTenant.getAddress().getAdditionalAddress());
        assertEquals(tenant.getAddress().getZipCode(), modifiedTenant.getAddress().getZipCode());
        assertEquals(tenant.getAddress().getTown(), modifiedTenant.getAddress().getTown());
        // Vérification que la méthode modifyTenant lève bien une exception NotFoundEntity
        assertThrows(NotFoundEntity.class, () -> tenantService.modifyTenant(tenant, UUID.randomUUID()));
        // Vérification que la méthode findById est appelée avec l'id de existingTenantEntity
        verify(tenantRepository).findById(existingTenantEntity.getId());
        // Vérification que la méthode save est appelée avec le TenantEntity modifié
        verify(tenantRepository).save(any(TenantEntity.class));
    }

    @Test
    public void testDeleteTenant() throws Exception {
        // Création d'un locataire factice
        UUID id = UUID.randomUUID();
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setFirstName("John");
        tenant.setLastName("Doe");
        tenant.setEmail("doe@example.com");
        tenant.setPhoneNumber("0787654321");
        tenant.setPartnerFirstName("Jane");
        tenant.setPartnerLastName("Doe");
        tenant.setPartnerPhoneNumber("0787654322");

        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setFirstName("John");
        tenantEntity.setLastName("Doe");
        tenantEntity.setEmail("doe@example.com");
        tenantEntity.setPhoneNumber("0787654321");
        tenantEntity.setPartnerFirstName("Jane");
        tenantEntity.setPartnerLastName("Doe");
        tenantEntity.setPartnerPhoneNumber("0787654322");

        Address address = new Address();
        tenant.setAddress(address);
        address.setStreet("1 rue des Lilas");
        address.setAdditionalAddress("Bat C1 apt 22");
        address.setZipCode("33130");
        address.setTown("Bègles");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("1 rue des Lilas");
        addressEntity.setAdditionalAddress("Bat C1 apt 22");
        addressEntity.setZipCode("33130");
        addressEntity.setTown("Bègles");
        tenantEntity.setAddress(addressEntity);

        // Mock du repository pour renvoyer le locataire factice
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenantEntity));

        // Appel de la méthode à tester
        tenantService.deleteTenant(id);

        // Vérification que la méthode delete du repository a bien été appelée avec le tenantEntity
        verify(tenantRepository, times(1)).delete(tenantEntity);
    }

}
