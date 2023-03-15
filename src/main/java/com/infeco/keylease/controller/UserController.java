package com.infeco.keylease.controller;

import com.infeco.keylease.entity.UserEntity;
import com.infeco.keylease.models.User;
import com.infeco.keylease.security.AuthoritiesConstants;
import com.infeco.keylease.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.USER + "')")
    public User getMe() throws UserPrincipalNotFoundException {
        UserEntity userEntity = this.userService.getCurrentUser();
        return new User(userEntity);
    }
}
