package com.jonathan.todolist.handler;

import com.jonathan.todolist.exception.NotFoundRoleException;
import com.jonathan.todolist.exception.TaskNotFoundException;
import com.jonathan.todolist.exception.UserFoundException;
import com.jonathan.todolist.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice //   Tratamento global de exceções em APIRest
public class GlobalExceptionHendler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserFoundException.class) //para pegar a classe que eu criei
    public ResponseEntity<String> userFoundExceptionHandler (UserFoundException userFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
    }
    undRoleException.class) //para pegar a classe que eu criei
    public ResponseEntity<S
    @ExceptionHandler(NotFotring> notFoundRoleExceptionHandler (NotFoundRoleException notFoundRoleExceptionHandler){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe nenhum usuário com essa Role!");
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> taskNotFoundException (TaskNotFoundException taskNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada!");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException (UserNotFoundException userNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
    }

}
