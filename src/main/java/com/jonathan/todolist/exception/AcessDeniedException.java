package com.jonathan.todolist.exception;

public class AcessDeniedException extends RuntimeException {
    public AcessDeniedException() {
        super("Acesso negado");
    }
}
