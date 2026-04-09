package com.jonathan.todolist.controller;

import com.jonathan.todolist.repository.UserRepository;
import com.jonathan.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    /*
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data ){

      String token = loginAuthorizationService.LoginAuthorization(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    */
     /*

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data){
        newUserAuthorizationService.NewUser(data);
        return ResponseEntity.ok().build();
    }
    */

    @GetMapping("/dados")
    public ResponseEntity dados(){
        return ResponseEntity.ok(userService.mostrardados());
    }


}
