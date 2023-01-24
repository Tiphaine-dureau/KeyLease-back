package com.infeco.keylease.service;

import com.infeco.keylease.models.User;
import com.infeco.keylease.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll().stream().map(User::new).collect(Collectors.toList());
    }
}
