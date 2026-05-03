package com.jonathan.todolist.service;


import com.jonathan.todolist.dto.TaskResponseDTO;
import com.jonathan.todolist.dto.TaskUpdateDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
import com.jonathan.todolist.exception.TaskNotFoundException;
import com.jonathan.todolist.model.TaskModel;
import com.jonathan.todolist.model.UserModel;
import com.jonathan.todolist.repository.TaskRepository;
import com.jonathan.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    public List<TaskResponseDTO> listMyTasks(String login){
        UserModel user = userRepository.findByLogin(login);
        return taskRepository.findByUser(user).stream().map( task -> new TaskResponseDTO(
                task.getTitulo(), task.getDataInicio(),task.getDataFim()
        )).toList();
    }

}
