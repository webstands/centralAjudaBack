package com.elbio.centraldeajuda.controller;

import com.elbio.centraldeajuda.controller.dto.LoginRequest;
import com.elbio.centraldeajuda.controller.dto.LoginResponse;
import com.elbio.centraldeajuda.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TokenController {

    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return tokenService.login(loginRequest);
    }
}
