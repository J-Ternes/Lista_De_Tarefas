package com.jonathan.todolist.dto;

import java.time.LocalDateTime;

public record TaskResponseDTO(String titulo, LocalDateTime dataInicio,  LocalDateTime dataFim ) {
}
