package com.jonathan.todolist.service;

import com.jonathan.todolist.config.TokenConfig;
import com.jonathan.todolist.dto.LoginRequestDTO;
import com.jonathan.todolist.dto.LoginResponseDTO;
import com.jonathan.todolist.dto.TaskUpdateDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
import com.jonathan.todolist.exception.TaskNotFoundException;
import com.jonathan.todolist.model.TaskModel;
import com.jonathan.todolist.model.UserModel;
import com.jonathan.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class TaskServiceAuth {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenConfig tokenConfig;

    public LoginResponseDTO loginAuthToken(LoginRequestDTO request){

        //Autentica o usuario utilizando o login e a senha
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        UserModel user = (UserModel) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);

        return new LoginResponseDTO(token);
    }


    public TaskModel create(TasksRegisterDTO task, UserModel logUser){
        TaskModel newTasks = new TaskModel();
        newTasks.setDescricao(task.descricao());
        newTasks.setTitulo(task.titulo());
        newTasks.setDataInicio(task.dataInicio());
        newTasks.setDataFim(task.dataFim());
        newTasks.setFinalizarTarefa(false);
        newTasks.setIdUser(logUser);

        return this.taskRepository.save(newTasks);

    }

    public void delete(UUID id){
        TaskModel task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        task.setFinalizarTarefa(true);
        taskRepository.save(task);
    }

    public void partialUpdate(UUID id, TaskUpdateDTO taskUpdate){
        TaskModel task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        //Atualiza o título. OBS: .isBlanck() so funciona para String por que ela pode vim vazia: "  " ou ""
        if(taskUpdate.titulo() != null && !taskUpdate.titulo().isBlank()) task.setTitulo(taskUpdate.titulo());
        //Atualiza a data ou horário final
        if(taskUpdate.dataFim() != null ) task.setDataFim(taskUpdate.dataFim());
        //Atualiza a data ou horário inicial
        if(taskUpdate.dataInicio() != null ) task.setDataInicio(taskUpdate.dataInicio());

        taskRepository.save(task);
    }
}
