package com.jonathan.todolist.users.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("Usuário já existe!");
    }
}
