package com.infeco.keylease;

import com.infeco.keylease.entity.UserEntity;
import com.infeco.keylease.models.User;
import com.infeco.keylease.repository.UserRepository;
import com.infeco.keylease.service.UserService;
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
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void testGetUsers() throws Exception {
        User user = new User();
        user.setFirsName("Firstname");
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Firstname");
        List<User> users = List.of(user);
        List<UserEntity> userEntities = List.of(userEntity);
        given(userRepository.findAll()).willReturn(userEntities);
        List<User> expected = userService.getUsers();
        assert (expected.get(0).getFirsName()).equals(users.get(0).getFirsName());
        verify(userRepository).findAll();
    }
}
