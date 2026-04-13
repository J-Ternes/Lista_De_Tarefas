package com.jonathan.todolist.dto;

import java.time.LocalDateTime;

public record TasksRegisterDTO(String titulo, String descricao, LocalDateTime dataInicio, LocalDateTime dataFim ) {
}
