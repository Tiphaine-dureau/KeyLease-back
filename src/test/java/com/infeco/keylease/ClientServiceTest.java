package com.infeco.keylease;

import com.infeco.keylease.entity.ClientEntity;
import com.infeco.keylease.entity.TenantEntity;
import com.infeco.keylease.repository.ClientRepository;
import com.infeco.keylease.service.ClientService;
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
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    public void testGetClients(){
        ClientEntity clientEntity = new TenantEntity();
        clientEntity.setFirstName("Firstname");
        List<ClientEntity> clients = List.of(clientEntity);
        given(clientRepository.findAll()).willReturn(clients);
        List<ClientEntity> expected = clientService.getClients();
        assertEquals(expected, clients);
        verify(clientRepository).findAll();
    }
}
