package com.jonathan.todolist.users.service;

import com.jonathan.todolist.users.exceptions.UserFoundException;
import com.jonathan.todolist.users.model.RegisterDTO;
import com.jonathan.todolist.users.model.UserModel;
import com.jonathan.todolist.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class NewUserAuthorizationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel NewUser(RegisterDTO data){

        if(this.userRepository.findByLogin(data.login()) != null) {
            throw new UserFoundException();
        }

        String encryptedPassword = passwordEncoder.encode(data.password());

        //Irá criptografar a senha
        UserModel newUser = new UserModel(data.login(), encryptedPassword, data.role());

        return this.userRepository.save(newUser);
    }
}
