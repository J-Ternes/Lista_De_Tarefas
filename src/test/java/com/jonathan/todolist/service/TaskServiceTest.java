package com.jonathan.todolist.service;

import com.jonathan.todolist.dto.TaskResponseDTO;
import com.jonathan.todolist.model.TaskModel;
import com.jonathan.todolist.model.UserModel;
import com.jonathan.todolist.repository.TaskRepository;
import com.jonathan.todolist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Habilita o Mockito
public class TaskServiceTest {

    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserRepository userRepository;

    private UserModel userModel;
    private TaskModel taskModel;

    @BeforeEach
    void setUp(){
        userModel = new UserModel();
        userModel.setLogin("jonathan");

        taskModel = new TaskModel();
        taskModel.setTitulo("Primeiro Test Unitario");
        taskModel.setDataInicio(LocalDateTime.now());
        taskModel.setDataFim(LocalDateTime.now().plusDays(1));
        taskModel.setIdUser(userModel);
    }

    @Test
    @DisplayName("Retorna as tasks do usuario paginada")
    void listMyTasks_sucess(){
        PageRequest pageable = PageRequest.of(0,10);
        Page<TaskModel> taskPage = new PageImpl<>(List.of(taskModel));

        when(userRepository.findByLogin("jonathan")).thenReturn(userModel);
        when(taskRepository.findByUser(userModel,pageable)).thenReturn(taskPage);

        Page<TaskResponseDTO> result = taskService.listMyTasks("jonathan",pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0).titulo()).isEqualTo("Primeiro Test Unitario");

        verify(userRepository, times(1)).findByLogin("jonathan");
        verify(taskRepository, times(1)).findByUser(userModel, pageable);
    }

    @Test
    @DisplayName("Deve retornar página vazia quando usuário não tem tasks")
    void listMyTasks_emptyPage() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<TaskModel> emptyPage = new PageImpl<>(List.of());

        when(userRepository.findByLogin("jonathan")).thenReturn(userModel);
        when(taskRepository.findByUser(userModel, pageable)).thenReturn(emptyPage);

        Page<TaskResponseDTO> result = taskService.listMyTasks("jonathan", pageable);

        assertThat(result).isEmpty();
    }
}




