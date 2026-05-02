package com.jonathan.todolist.service;


import com.jonathan.todolist.dto.TaskResponseDTO;
import com.jonathan.todolist.dto.TaskUpdateDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
import com.jonathan.todolist.exception.TaskNotFoundException;
import com.jonathan.todolist.model.TaskModel;
import com.jonathan.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public List<TaskResponseDTO> getAll(){
        return taskRepository.findByFinalizarTarefaFalse().stream().map( task -> new TaskResponseDTO(
                task.getTitulo(), task.getDataInicio(),task.getDataFim()
        )).toList();
    }

}
