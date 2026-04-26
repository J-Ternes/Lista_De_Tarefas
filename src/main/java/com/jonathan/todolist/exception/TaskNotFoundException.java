package com.jonathan.todolist.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Task não encontrada");
    }
}
