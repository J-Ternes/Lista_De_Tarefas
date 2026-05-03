package com.jonathan.todolist.config;

import com.jonathan.todolist.model.TaskModel;
import com.jonathan.todolist.model.UserModel;
import com.jonathan.todolist.repository.TaskRepository;
import com.jonathan.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TaskCleanupService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;


    @Scheduled(cron = "0 0 0 * * *") //Roda todos os dias à meia noite
    public void deleteInactiveTasks(){
        LocalDateTime tempoDeCorte = LocalDateTime.now().minusMinutes(1);

        //Busca tasks que foram finalizadas e que foram atualizadas há mais de 7 dias
        List<TaskModel> tasksInativas = taskRepository.findByFinalizarTarefaTrueAndAtualizadoEm(tempoDeCorte);


        if(!tasksInativas.isEmpty()){
            taskRepository.deleteAll(tasksInativas);
            System.out.println("Deletado: " + tasksInativas.size() + "tasks inativas");
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Roda todos os dias à meia noite
    public void deleteInactiveUsers() {
        LocalDateTime tempoDeCorte = LocalDateTime.now().minusMinutes(1);

        List<UserModel> usuariosInativos = userRepository.findByActiveFalseAndAtualizadoEmBefore(tempoDeCorte);

        if (!usuariosInativos.isEmpty()) {
            userRepository.deleteAll(usuariosInativos);
            System.out.println("Deletado: " + usuariosInativos.size() + " usuários inativos");
        }
    }
}
