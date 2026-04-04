package com.jonathan.todolist.users.model;

public record RegisterDTO(String login, String password, UserRole role) {
}
