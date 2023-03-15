package com.infeco.keylease.controller;

import com.infeco.keylease.entity.UserEntity;
import com.infeco.keylease.repository.UserRepository;
import com.infeco.keylease.security.AuthenticationRequest;
import com.infeco.keylease.security.AuthenticationResponse;
import com.infeco.keylease.security.RegisterRequest;
import com.infeco.keylease.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {
    private final LoginService loginService;
    private final UserRepository userRepository;

    public LoginController(LoginService loginService, UserRepository userRepository) {
        this.loginService = loginService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        Optional<UserEntity> existingUser = this.userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(loginService.register(request));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(loginService.authenticate(request));
    }
}
