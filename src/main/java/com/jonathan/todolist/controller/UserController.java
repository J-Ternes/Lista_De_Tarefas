package com.jonathan.todolist.controller;

import com.jonathan.todolist.config.JWTUserData;
import com.jonathan.todolist.dto.UserRegisterDTO;
import com.jonathan.todolist.dto.UserUpdateDTO;
import com.jonathan.todolist.model.UserRole;
import com.jonathan.todolist.repository.UserRepository;
import com.jonathan.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody UserRegisterDTO data){
        userService.cadastrarNewUser(data);

        return ResponseEntity.ok("Novo cadastro efetuado com sucesso");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dados")
    public ResponseEntity dados(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/role/{role}")
    public ResponseEntity buscaRole(@PathVariable UserRole role){
        return ResponseEntity.ok( userService.buscarPorRole(role));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{login}")
    public ResponseEntity delete(@PathVariable String login){
        userService.delete(login);
        return ResponseEntity.noContent().build(); //Retorna Http 204 (Padrao Rest)
    }


    @PatchMapping("/update/{login}")
    public ResponseEntity updatePartial(@PathVariable String login, @RequestBody UserUpdateDTO data ){
         userService.updateLogin(login,data);
        return ResponseEntity.ok("Atualização efetuada com sucesso!");
    }

    @PatchMapping("/promote/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity promoteUser(@PathVariable UUID id){
        userService.promoteToAdmin(id);
        return ResponseEntity.ok("Novo Admin cadastrado!");
    }

    @PatchMapping("/demote/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity demoteUSer(@PathVariable UUID id, @AuthenticationPrincipal JWTUserData logAdmin){
        userService.demoteToUser(id, logAdmin.login());
        return ResponseEntity.ok("Rebaixado para USER!");
    }

}
