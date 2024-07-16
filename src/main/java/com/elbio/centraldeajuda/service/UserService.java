package com.elbio.centraldeajuda.service;

import com.elbio.centraldeajuda.controller.dto.CreateUserDto;
import com.elbio.centraldeajuda.controller.dto.RoleDto;
import com.elbio.centraldeajuda.controller.dto.UserResponse;
import com.elbio.centraldeajuda.entities.Call;
import com.elbio.centraldeajuda.entities.User;
import com.elbio.centraldeajuda.repository.CallRepository;
import com.elbio.centraldeajuda.repository.RoleRepository;
import com.elbio.centraldeajuda.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserService {

    private  UserRepository userRepository;
    private  RoleRepository roleRepository;
    private  BCryptPasswordEncoder passwordEncoder;
    private  CallRepository callRepository;


    public ResponseEntity<Boolean> registerOrUpdateUser(@RequestBody CreateUserDto dto, UUID id) {
        var basicRole = roleRepository.findByName(dto.permissao());

        User userFromDb = userRepository.findByUserId(id);

        if (Objects.nonNull(userFromDb)) {
            User user = userFromDb;
            user.setName(dto.name());
            user.setEmail(dto.email());
            user.setRoles(new HashSet<>(Set.of(basicRole)));

            userRepository.save(user);

            return ResponseEntity.ok(Boolean.TRUE);
        } else {
            User user = new User();
            user.setUsername(dto.username());
            user.setPassword(passwordEncoder.encode(dto.password()));
            user.setName(dto.name());
            user.setEmail(dto.email());
            user.setRoles(Set.of(basicRole));

            userRepository.save(user);

            return ResponseEntity.ok(Boolean.TRUE);
        }
    }

    public ResponseEntity<List<UserResponse>> listUsers() {
        var users = userRepository.findAll();
        var userDtos = users.stream()
                .map(user -> new UserResponse(
                        user.getUserId(),
                        user.getUsername(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(role -> new RoleDto(role.getRoleId(), role.getName()))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    public ResponseEntity<Boolean> deleteUser(UUID id) {

        User userFromDb = userRepository.findByUserId(id);

        List<Call> chamados = callRepository.findByUser_UserId(id);

        if(!chamados.isEmpty()){
            throw new RuntimeException("Não foi possivel realizar a exclusão existem chamados vinculados ao User");
        }

        if (Objects.nonNull(userFromDb)) {
            userRepository.delete(userFromDb);
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }

}
