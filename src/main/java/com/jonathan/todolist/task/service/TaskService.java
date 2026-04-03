package com.jonathan.todolist.task.service;


import com.jonathan.todolist.task.model.TaskModel;
import com.jonathan.todolist.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public TaskModel execute(TaskModel taskModel){
        return this.taskRepository.save(taskModel);
    }

}
