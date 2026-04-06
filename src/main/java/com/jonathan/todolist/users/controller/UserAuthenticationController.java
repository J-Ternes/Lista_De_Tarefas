package com.jonathan.todolist.users.controller;

import com.jonathan.todolist.configurations.security.TokenService;
import com.jonathan.todolist.users.exceptions.UserFoundException;
import com.jonathan.todolist.users.model.AuthenticationDTO;
import com.jonathan.todolist.users.model.LoginResponseDTO;
import com.jonathan.todolist.users.model.RegisterDTO;
import com.jonathan.todolist.users.model.UserModel;
import com.jonathan.todolist.users.repository.UserRepository;
import com.jonathan.todolist.users.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationService authorizationService;




    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data ){

      String token = authorizationService.LoginAuthorization(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data){

        if(this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password()); //Irá criptografar a senha
        UserModel newUser = new UserModel(data.login(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();

    }
}
