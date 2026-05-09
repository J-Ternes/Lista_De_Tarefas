package com.jonathan.todolist.controller;

import com.jonathan.todolist.config.JWTUserData;
import com.jonathan.todolist.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Operações relacionadas às tarefas")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Operation(summary = "Listar todas as tarefas", description = "Retorna todas as tarefas do usuário logado")
    @ApiResponse(responseCode = "200", description = "Tarefas retornadas com sucesso")
    @PreAuthorize("hasRole('USER')") //Apenas USER pode ver as tasks
    @GetMapping("/dados")
    //@PageableDefault -> Paginação
    public ResponseEntity getAll(@AuthenticationPrincipal JWTUserData logUser, @PageableDefault(size = 5, sort = "dataInicio", direction = Sort.Direction.ASC)Pageable pageable) //Recebe o token e autentica
     {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.listMyTasks(logUser.login(), pageable));
    }



}
