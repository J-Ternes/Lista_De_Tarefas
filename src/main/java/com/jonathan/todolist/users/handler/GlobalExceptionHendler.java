package com.jonathan.todolist.users.handler;

import com.jonathan.todolist.users.exceptions.UserFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice //   Tratamento global de exceções em APIRest
public class GlobalExceptionHendler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserFoundException.class) //para pegar a classe que eu criei
    public ResponseEntity<String> userFoundExceptionHendler (UserFoundException userFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
    }
}
