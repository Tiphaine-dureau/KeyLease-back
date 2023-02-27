package com.infeco.keylease;

import com.infeco.keylease.repository.OwnerRepository;
import com.infeco.keylease.security.AuthoritiesConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetOwnersWithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(status().isOk());
    }
}
