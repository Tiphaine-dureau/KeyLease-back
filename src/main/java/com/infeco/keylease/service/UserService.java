package com.infeco.keylease.service;

import com.infeco.keylease.entity.UserEntity;
import com.infeco.keylease.models.User;
import com.infeco.keylease.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<UserEntity> entities = this.userRepository.findAll();
        return entities.stream().map(User::new).collect(Collectors.toList());
    }

    public UserEntity getCurrentUser() throws UserPrincipalNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UserPrincipalNotFoundException("User not found"));
    }
}
