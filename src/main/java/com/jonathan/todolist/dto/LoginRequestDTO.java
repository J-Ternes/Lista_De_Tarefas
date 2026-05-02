package com.jonathan.todolist.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDTO(@NotEmpty(message = "Login é obrigatótio") String login,
                              @NotEmpty(message = "Senha é obrigatória") String password) {
}
