package com.jonathan.todolist.dto;

import com.jonathan.todolist.model.UserRole;

public record UserResponseDTO(String login, UserRole role) {
}
