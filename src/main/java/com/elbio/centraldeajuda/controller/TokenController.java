package com.elbio.centraldeajuda.controller;

import com.elbio.centraldeajuda.controller.dto.LoginRequest;
import com.elbio.centraldeajuda.controller.dto.LoginResponse;
import com.elbio.centraldeajuda.entities.Role;
import com.elbio.centraldeajuda.repository.UserRepository;
import com.elbio.centraldeajuda.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TokenController {

    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return tokenService.login(loginRequest);
    }
}
