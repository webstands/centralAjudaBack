package com.elbio.centraldeajuda.controller.dto;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

public record CallDto(Long id, String subject, String description, LocalDateTime createdAt, LocalDateTime closedAt, boolean closed, java.util.UUID userId, java.util.UUID closedById, int rating) {
}
