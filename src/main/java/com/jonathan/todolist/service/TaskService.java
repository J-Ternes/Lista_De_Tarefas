package com.jonathan.todolist.service;


import com.jonathan.todolist.dto.TaskResponseDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
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
        return taskRepository.findByFinalizarTarefaTrue().stream().map( task -> new TaskResponseDTO(
                task.getTitulo(), task.getDataInicio(),task.getDataFim()
        )).toList();
    }

    public void delete(UUID id){
        TaskModel task = taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Tarefa nao encontrada"));
        task.setFinalizarTarefa(true);
        taskRepository.save(task);
    }
}
