package com.jonathan.todolist.exception;

public class NotFoundRoleException extends RuntimeException {
    public NotFoundRoleException() {
        super("Não existe nenhum usuário com essa Role");
    }
}
