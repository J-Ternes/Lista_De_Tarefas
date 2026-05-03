package com.jonathan.todolist.handler;

import com.jonathan.todolist.dto.ErrorResponseDTO;
import com.jonathan.todolist.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice //   Tratamento global de exceções em APIRest
public class GlobalExceptionHendler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserFoundException.class) //para pegar a classe que eu criei
    public ResponseEntity<ErrorResponseDTO> userFoundExceptionHandler (UserFoundException userFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(400,"Usuário já existe",LocalDateTime.now()));
    }

    @ExceptionHandler(NotFoundRoleException.class) //para pegar a classe que eu criei
    public ResponseEntity<String> notFoundRoleExceptionHandler (NotFoundRoleException notFoundRoleExceptionHandler){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe nenhum usuário com essa Role!");
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> taskNotFoundExceptionHandler (TaskNotFoundException taskNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404,"Tarefa não encontrada",LocalDateTime.now()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> userNotFoundExceptionHandler (UserNotFoundException userNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(404,"Usuário não encontrado",LocalDateTime.now()));
    }

    @ExceptionHandler(AcessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> acessDeniedExceptionHandler (AcessDeniedException acessDeniedException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO(403,"Acesso negado!", LocalDateTime.now()));
    }

    /*


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> genericExceptionHandler(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(500,"Erro interno no servidor",LocalDateTime.now()));
    }
    */




}
