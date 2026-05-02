package com.jonathan.todolist.service;

import com.jonathan.todolist.dto.UserRegisterDTO;
import com.jonathan.todolist.dto.UserUpdateDTO;
import com.jonathan.todolist.dto.UserResponseDTO;
import com.jonathan.todolist.exception.NotFoundRoleException;
import com.jonathan.todolist.exception.UserFoundException;
import com.jonathan.todolist.exception.UserNotFoundException;
import com.jonathan.todolist.model.UserModel;
import com.jonathan.todolist.model.UserRole;
import com.jonathan.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findByActiveTrue().stream().map(user -> new UserResponseDTO(
                user.getLogin(), user.getRole())).toList();
    }

    public UserModel cadastrarNewUser(UserRegisterDTO data) {
        UserModel user = userRepository.findByLogin(data.login());

        if(user != null) throw new UserFoundException();

        UserModel newUser = new UserModel();
        newUser.setPassword(passwordEncoder.encode(data.password()));
        newUser.setLogin(data.login());
        newUser.setActive(true);
        newUser.setRole(UserRole.USER);

        return userRepository.save(newUser);
    }

    public List<UserResponseDTO> buscarPorRole(UserRole role) {
        var resultRole = userRepository.findByRole(role);
        if (resultRole.isEmpty()) throw new NotFoundRoleException();
        return resultRole.stream().map(user -> new UserResponseDTO(
                user.getLogin(), user.getRole())).toList();
    }

    public void delete(String login) {
        UserModel user = userRepository.findByLogin(login);

        if (user == null) throw new UserNotFoundException();
        //userRepository.delete(user) -> //Hard Delete -> Não
        user.setActive(false); //Soft Delete (È mais seguro) nao perde o dado para sempre
        userRepository.save(user);
    }

    public void updateLogin(String currentLogin, UserUpdateDTO data) {
        UserModel user = userRepository.findByLogin(currentLogin);

        if (user == null) throw new UserNotFoundException();

        //Verifica se o novo login escolhido pelo usuário existe
        if (userRepository.findByLogin(data.login()) != null) throw new UserFoundException();

        user.setLogin(data.login());
       userRepository.save(user);
    }

    public void promoteToAdmin (UUID id){
        UserModel userId = userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
         userId.setRole(UserRole.ADMIN);
         userRepository.save(userId);
    }
}