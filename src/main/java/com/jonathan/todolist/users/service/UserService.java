package com.jonathan.todolist.users.service;

import com.jonathan.todolist.users.exceptions.UserFoundException;
import com.jonathan.todolist.users.model.UserModel;
import com.jonathan.todolist.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserModel execute(UserModel userModel){
        /*
        this.userRepository.findByLogin(userModel.getLogin()).ifPresent((UserModel user)-> {
            throw new UserFoundException();
            });

            É interessante usar o .orElseThrow(),
            acredito ser mais perfomático.
            Mas como o banco é o H2, sempre dará o exception

            * */

        return this.userRepository.save(userModel);
    }
}
