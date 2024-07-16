package com.elbio.centraldeajuda.controller;

import com.elbio.centraldeajuda.controller.dto.CreateUserDto;
import com.elbio.centraldeajuda.controller.dto.UserResponse;
import com.elbio.centraldeajuda.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {

    private UserService userService;


    @Transactional
    @PostMapping
    public ResponseEntity<Boolean> registerUser(@RequestBody CreateUserDto dto) {
        return userService.registerOrUpdateUser(dto, null);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserResponse>> listUsers() {
        return  userService.listUsers();
    }

    @Transactional
    @PutMapping(path= "/{id}")
    public ResponseEntity<Boolean> updateUser(@RequestBody CreateUserDto dto, @PathVariable UUID id) {
        return userService.registerOrUpdateUser(dto, id);
    }

    @Transactional
    @DeleteMapping(path= "/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(id);
    }
}
