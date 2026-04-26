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
        return taskRepository.findByFinalizarTarefaFalse().stream().map( task -> new TaskResponseDTO(
                task.getTitulo(), task.getDataInicio(),task.getDataFim()
        )).toList();
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
