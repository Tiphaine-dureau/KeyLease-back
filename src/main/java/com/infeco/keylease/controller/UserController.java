package com.infeco.keylease.controller;

import com.infeco.keylease.entity.UserEntity;
import com.infeco.keylease.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        return this.userService.getUsers();
    }

}
