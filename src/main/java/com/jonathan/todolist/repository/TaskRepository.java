package com.jonathan.todolist.repository;

import com.jonathan.todolist.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {


    List<TaskModel> findByFinalizarTarefaFalse();
    TaskModel findByIdAndFinalizarTarefaTrue(UUID id);

    List<TaskModel> findByFinalizarTarefaTrueAndAtualizadoEm(LocalDateTime date);


}
