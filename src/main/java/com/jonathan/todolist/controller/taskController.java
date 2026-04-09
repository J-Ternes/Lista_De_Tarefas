package com.jonathan.todolist.controller;

import com.jonathan.todolist.model.TaskModel;
import com.jonathan.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class taskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody TaskModel taskModel){
        var result = taskService.execute(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskModel);
    }
}
