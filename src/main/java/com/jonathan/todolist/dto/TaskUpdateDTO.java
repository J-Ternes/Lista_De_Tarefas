package com.jonathan.todolist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record TaskUpdateDTO(String titulo, @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss") LocalDateTime dataInicio, @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")LocalDateTime dataFim) {
}
