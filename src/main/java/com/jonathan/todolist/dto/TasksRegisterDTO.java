package com.jonathan.todolist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record TasksRegisterDTO(

        @Schema(description = "Título da tarefa", example = "Estudar Java")
        String titulo,

        @Schema(description = "Descrição da tarefa", example = "Estudar documentação com SWAGGER")
        String descricao,

        @Schema(description = "Data de início", example = "01/06/2025 08:00:00")
        @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss") LocalDateTime dataInicio,

        @Schema(description = "Data de fim", example = "01/06/2025 18:00:00")
        @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")LocalDateTime dataFim ) {
}
