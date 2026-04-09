package com.jonathan.todolist.service;

import com.jonathan.todolist.dto.ResponseDTO;
import com.jonathan.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<ResponseDTO> mostrardados(){
       return userRepository.findAll().stream().map(user -> new ResponseDTO(
               user.getLogin(),user.getRole())).toList();
    }
}
