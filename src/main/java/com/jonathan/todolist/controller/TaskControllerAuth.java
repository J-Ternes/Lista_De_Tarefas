package com.jonathan.todolist.controller;

import com.jonathan.todolist.config.JWTUserData;
import com.jonathan.todolist.dto.LoginRequestDTO;
import com.jonathan.todolist.dto.LoginResponseDTO;
import com.jonathan.todolist.dto.TaskUpdateDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
import com.jonathan.todolist.repository.UserRepository;
import com.jonathan.todolist.service.TaskServiceAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class TaskControllerAuth {

    @Autowired
    TaskServiceAuth taskServiceAuth;

    @Autowired
    UserRepository userRepository;


    @Operation(summary = "Faça o login para criar o token", description = "Com o token e a role USER você poderá criar, atualizar e deletar tarefas")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO requestDTO){
        LoginResponseDTO response = taskServiceAuth.loginAuthToken(requestDTO);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Criar tarefa", description = "Apenas Usuários com a role Admin podem criar novas tarefas")
    @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TasksRegisterDTO registerDTO,
                                 @AuthenticationPrincipal JWTUserData logUser){
        var result = taskServiceAuth.create(registerDTO, logUser.login());
        return ResponseEntity.status(HttpStatus.CREATED).body(registerDTO);
    }

    @Operation(summary = "Deletar tarefa", description = "Apenas Usuários com a role Admin podem deletar as tarefas")
    @ApiResponse(responseCode = "204", description = "Tarefa deletada")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable UUID id, @AuthenticationPrincipal JWTUserData loggedUser){
        taskServiceAuth.delete(id, loggedUser.login());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody TaskUpdateDTO taskUpdate,@AuthenticationPrincipal JWTUserData loggedUser){
        taskServiceAuth.partialUpdate(id, taskUpdate, loggedUser.login());
        return ResponseEntity.ok("Atualização realizada com sucesso!");

    }



}
