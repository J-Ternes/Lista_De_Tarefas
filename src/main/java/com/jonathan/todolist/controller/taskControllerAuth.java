package com.jonathan.todolist.controller;

import com.jonathan.todolist.dto.LoginRequestDTO;
import com.jonathan.todolist.dto.LoginResponseDTO;
import com.jonathan.todolist.dto.TaskUpdateDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
import com.jonathan.todolist.repository.UserRepository;
import com.jonathan.todolist.service.TaskServiceAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class taskControllerAuth {

    @Autowired
    TaskServiceAuth taskServiceAuth;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO requestDTO){
        LoginResponseDTO response = taskServiceAuth.loginAuthToken(requestDTO);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TasksRegisterDTO registerDTO){
        var result = taskServiceAuth.create(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        taskServiceAuth.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody TaskUpdateDTO taskUpdate){
        taskServiceAuth.partialUpdate(id, taskUpdate);
        return ResponseEntity.ok("Atualização realizada com sucesso!");

    }

}
