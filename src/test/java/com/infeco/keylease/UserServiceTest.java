package com.infeco.keylease;

import com.infeco.keylease.entity.UserEntity;
import com.infeco.keylease.models.User;
import com.infeco.keylease.repository.UserRepository;
import com.infeco.keylease.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    UserService userService;

    @Test
    public void testGetUsers() throws Exception {
        User user = new User();
        user.setFirstName("Firstname");
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Firstname");
        List<User> users = List.of(user);
        List<UserEntity> userEntities = List.of(userEntity);
        given(userRepository.findAll()).willReturn(userEntities);
        List<User> expected = userService.getUsers();
        assert (expected.get(0).getFirstName()).equals(users.get(0).getFirstName());
        verify(userRepository).findAll();
    }

    @Test
    public void testGetCurrentUser() throws UserPrincipalNotFoundException {
        // Création utilisateur factice
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("azerty12345");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword("azerty12345");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(email)
                .password("azerty12345")
                .roles("USER")
                .build();

        // Mock de l'authentification et du user details
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

        // Mock du user repository
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        // Exécution de la méthode à tester
        UserEntity currentUser = userService.getCurrentUser();
        // Vérification du résultat
        assertEquals(userEntity, currentUser);
    }
}
