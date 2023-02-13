package com.infeco.keylease;

import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.repository.TenantRepository;
import com.infeco.keylease.service.TenantService;
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
public class TenantServiceTest {

    @Mock
    private TenantRepository tenantRepository;

    @InjectMocks
    TenantService tenantService;

    @Test
    public void testGetTenant(){
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setPartnerFirstName("PartnerFirstName");
        tenantEntity.setPartnerLastName("PartnerLastName");
        tenantEntity.setFirstName("Firstname");
        tenantEntity.setEmail("email@test.com");
        List<TenantEntity> tenants = List.of(tenantEntity);
        given(tenantRepository.findAll()).willReturn(tenants);
        List<TenantEntity> expected = tenantService.getTenants();
        assertEquals(expected, tenants);
        verify(tenantRepository).findAll();
    }
}
