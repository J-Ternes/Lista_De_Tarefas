package com.jonathan.todolist.config;

import lombok.Builder;

@Builder
public record JWTUserData(String login, String role) {
}
