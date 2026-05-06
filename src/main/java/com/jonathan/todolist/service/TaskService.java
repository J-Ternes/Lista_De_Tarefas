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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    //Com paginação
    public Page<TaskResponseDTO> listMyTasks(String login, Pageable pageable){
        UserModel user = userRepository.findByLogin(login);
        return taskRepository.findByUser(user, pageable).map( task -> new TaskResponseDTO(
                task.getTitulo(), task.getDataInicio(),task.getDataFim()
        ));
    }

}
