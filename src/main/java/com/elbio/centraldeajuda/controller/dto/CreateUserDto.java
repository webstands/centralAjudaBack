package com.elbio.centraldeajuda.controller.dto;

public record CreateUserDto(String username, String password, String name, String email, String permissao) {
}
