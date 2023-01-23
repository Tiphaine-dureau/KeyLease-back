package com.infeco.keylease.service;

import com.infeco.keylease.entity.UserEntity;
import com.infeco.keylease.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserEntity> getUsers(){
        return this.userRepository.findAll();
    }
}
