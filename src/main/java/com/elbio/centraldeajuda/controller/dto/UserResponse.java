package com.elbio.centraldeajuda.controller.dto;

import java.util.Set;

public record UserResponse(java.util.UUID id, String username, Set<RoleDto> roles) {
}