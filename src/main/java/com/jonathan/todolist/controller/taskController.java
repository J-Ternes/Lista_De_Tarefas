package com.jonathan.todolist.controller;

import com.jonathan.todolist.dto.TaskUpdateDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
import com.jonathan.todolist.model.TaskModel;
import com.jonathan.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class taskController {

    @Autowired
    TaskService taskService;

    @PreAuthorize("hasRole('USER')") //Apenas USER pode ver as tasks
    @GetMapping("/dados")
    public ResponseEntity getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAll());
    }



}
