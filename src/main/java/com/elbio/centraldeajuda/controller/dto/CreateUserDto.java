package com.elbio.centraldeajuda.controller.dto;

import java.util.UUID;

public record CreateUserDto(String username, String password, String name, String email, String permissao) {
}
