package com.jonathan.todolist.dto;

import com.jonathan.todolist.model.UserRole;

public record ResponseDTO(String login, UserRole role) {
}
