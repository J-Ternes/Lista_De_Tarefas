package com.jonathan.todolist.controller;

import com.jonathan.todolist.dto.TaskUpdateDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
import com.jonathan.todolist.model.TaskModel;
import com.jonathan.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class taskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TasksRegisterDTO registerDTO){
        var result = taskService.execute(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerDTO);
    }

    @GetMapping("/dados")
    public ResponseEntity getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody TaskUpdateDTO taskUpdate){
        taskService.partialUpdate(id, taskUpdate);
        return ResponseEntity.ok("Atualização realizada com sucesso!");

    }
}
