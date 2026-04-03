package com.jonathan.todolist.users.controller;


import com.jonathan.todolist.users.model.UserModel;
import com.jonathan.todolist.users.repository.UserRepository;
import com.jonathan.todolist.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody UserModel userModel){
            var result = this.userService.execute(userModel);
            return ResponseEntity.ok().body(result);

    }

}
