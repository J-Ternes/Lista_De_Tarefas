package com.jonathan.todolist.controller;

import com.jonathan.todolist.dto.UserResponseDTO;
import com.jonathan.todolist.model.UserModel;
import com.jonathan.todolist.model.UserRole;
import com.jonathan.todolist.repository.UserRepository;
import com.jonathan.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody UserModel user){
        userService.cadastrarNewUser(user);

        return ResponseEntity.ok("Novo cadastro efetuado com sucesso");
    }

    @GetMapping("/dados")
    public ResponseEntity dados(){
        return ResponseEntity.ok(userService.mostrardados());
    }


    @GetMapping("/role/{role}")
    public ResponseEntity buscaRole(@PathVariable UserRole role){
        return ResponseEntity.ok( userService.buscarPorRole(role));
    }

    @DeleteMapping("/{login}")
    public ResponseEntity delete(@PathVariable String login){
        userService.delete(login);
        return ResponseEntity.noContent().build(); //Retorna Http 204 (Padrao Rest)
    }
}
