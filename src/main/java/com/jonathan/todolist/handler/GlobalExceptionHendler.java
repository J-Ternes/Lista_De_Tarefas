package com.jonathan.todolist.handler;

import com.jonathan.todolist.exception.NotFoundRoleException;
import com.jonathan.todolist.exception.UserFoundException;
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

    @ExceptionHandler(NotFoundRoleException.class) //para pegar a classe que eu criei
    public ResponseEntity<String> notFoundRoleExceptionHandler (NotFoundRoleException notFoundRoleExceptionHandler){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não existe nenhum usuário com essa Role!");
    }

}
