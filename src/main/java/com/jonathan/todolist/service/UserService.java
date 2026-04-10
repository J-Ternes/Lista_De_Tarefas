package com.jonathan.todolist.service;

import com.jonathan.todolist.dto.UserResponseDTO;
import com.jonathan.todolist.exception.NotFoundRoleException;
import com.jonathan.todolist.exception.UserFoundException;
import com.jonathan.todolist.model.UserModel;
import com.jonathan.todolist.model.UserRole;
import com.jonathan.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public List<UserResponseDTO> mostrardados(){
       return userRepository.findAll().stream().map(user -> new UserResponseDTO(
               user.getLogin(),user.getRole())).toList();
    }

    public UserModel cadastrarNewUser (UserModel user){
       var result = userRepository.findByLogin(user.getLogin());

        if(result != null) throw new UserFoundException();
        return userRepository.save(user);
    }

    public List<UserResponseDTO> buscarPorRole(UserRole role){
        var resultRole = userRepository.findByRole(role);
         if(resultRole.isEmpty()) throw new NotFoundRoleException();
         return resultRole.stream().map(user-> new UserResponseDTO(
                 user.getLogin(),user.getRole())).toList();
    }
}
