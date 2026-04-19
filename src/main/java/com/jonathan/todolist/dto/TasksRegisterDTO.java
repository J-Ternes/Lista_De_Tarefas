package com.jonathan.todolist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TasksRegisterDTO(String titulo, String descricao,
                               @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss") LocalDateTime dataInicio, @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")LocalDateTime dataFim ) {
}
