package com.elbio.centraldeajuda.controller;

import com.elbio.centraldeajuda.controller.dto.CreateUserDto;
import com.elbio.centraldeajuda.controller.dto.UserResponse;
import com.elbio.centraldeajuda.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> registerUser(@RequestBody CreateUserDto dto) {
        return userService.registerUser(dto);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserResponse>> listUsers() {
        return  userService.listUsers();
    }
}
