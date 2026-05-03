package com.jonathan.todolist.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int status,
        String mensage,
        LocalDateTime timestamp
) {}
