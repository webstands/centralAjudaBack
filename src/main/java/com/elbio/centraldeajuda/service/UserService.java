package com.elbio.centraldeajuda.service;

import com.elbio.centraldeajuda.controller.dto.CreateUserDto;
import com.elbio.centraldeajuda.controller.dto.RoleDto;
import com.elbio.centraldeajuda.controller.dto.UserResponse;
import com.elbio.centraldeajuda.entities.Role;
import com.elbio.centraldeajuda.entities.User;
import com.elbio.centraldeajuda.repository.RoleRepository;
import com.elbio.centraldeajuda.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserService {

    private  UserRepository userRepository;
    private  RoleRepository roleRepository;
    private  BCryptPasswordEncoder passwordEncoder;


    public ResponseEntity<Void> registerUser(@RequestBody CreateUserDto dto) {

        var basicRole = roleRepository.findByName(Role.Values.USUARIO.name());

        var userFromDb = userRepository.findByUsername(dto.username());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<UserResponse>> listUsers() {
        var users = userRepository.findAll();
        var userDtos = users.stream()
                .map(user -> new UserResponse(
                        user.getUserId(),
                        user.getUsername(),
                        user.getRoles().stream()
                                .map(role -> new RoleDto(role.getRoleId(), role.getName()))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
}
