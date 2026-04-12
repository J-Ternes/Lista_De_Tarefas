package com.jonathan.todolist.service;

import com.jonathan.todolist.dto.UserUpdateDTO;
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


    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findByActiveTrue().stream().map(user -> new UserResponseDTO(
                user.getLogin(), user.getRole())).toList();
    }

    public UserModel cadastrarNewUser(UserModel user) {
        var result = userRepository.findByLogin(user.getLogin());

        if (result != null) throw new UserFoundException();
        return userRepository.save(user);
    }

    public List<UserResponseDTO> buscarPorRole(UserRole role) {
        var resultRole = userRepository.findByRole(role);
        if (resultRole.isEmpty()) throw new NotFoundRoleException();
        return resultRole.stream().map(user -> new UserResponseDTO(
                user.getLogin(), user.getRole())).toList();
    }

    public void delete(String login) {
        UserModel user = userRepository.findByLogin(login);
        if (user == null) throw new RuntimeException("Usuário não encontrado");
        //userRepository.delete(user) -> //Hard Delete -> Não
        user.setActive(false);
        userRepository.save(user);
    }

    public void updatePartial(String currentLogin, UserUpdateDTO data) {
        UserModel user = userRepository.findByLogin(currentLogin);

        if (user == null) throw new RuntimeException("Usuário não encontrado");

        //Verifica se quer mudar login
        if (data.login() != null && data.login().isBlank()) {

            //Verifica se o novo login escolhido pelo usuário existe
            if (userRepository.findByLogin(data.login()) != null)
                throw new RuntimeException("Esse usuário já está em uso");

            user.setLogin(data.login());
        }

        //Verifica se quer mudar a senha
        if (data.password() != null && data.password().isBlank()) {
            user.setPassword(data.password());
        }
       userRepository.save(user);

    }
}