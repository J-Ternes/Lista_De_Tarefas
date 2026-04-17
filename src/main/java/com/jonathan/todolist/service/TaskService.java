package com.jonathan.todolist.service;


import com.jonathan.todolist.dto.TaskResponseDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
import com.jonathan.todolist.model.TaskModel;
import com.jonathan.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public TaskModel execute(TasksRegisterDTO task){
        TaskModel newTasks = new TaskModel();
        newTasks.setDescricao(task.descricao());
        newTasks.setTitulo(task.titulo());
        newTasks.setDataInicio(task.dataInicio());
        newTasks.setDataFim(task.dataFim());
        newTasks.setFinalizarTarefa(false);

        return this.taskRepository.save(newTasks);
    }

    public List<TaskResponseDTO> getAll(){
        return taskRepository.findAll().stream().map( task -> new TaskResponseDTO(
                task.getTitulo(), task.getDataInicio(),task.getDataFim()
        )).toList();
    }

}
