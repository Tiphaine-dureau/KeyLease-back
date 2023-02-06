package com.infeco.keylease;

import com.infeco.keylease.security.AuthoritiesConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class UserControllerTest {

    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    public void testGetClientsWithValidTokenAndRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .header("Authorization", "Bearer" + VALID_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.USER)
    public void testGetClientsWithInvalidRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .header("Authorization", "Bearer" + VALID_TOKEN))
                .andExpect(status().isForbidden());
    }
}

