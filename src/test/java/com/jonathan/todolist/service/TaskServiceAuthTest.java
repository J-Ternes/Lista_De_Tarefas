package com.jonathan.todolist.service;

import com.jonathan.todolist.config.TokenConfig;
import com.jonathan.todolist.dto.LoginRequestDTO;
import com.jonathan.todolist.dto.LoginResponseDTO;
import com.jonathan.todolist.dto.TaskUpdateDTO;
import com.jonathan.todolist.dto.TasksRegisterDTO;
import com.jonathan.todolist.exception.AcessDeniedException;
import com.jonathan.todolist.exception.TaskNotFoundException;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceAuthTest {

        @InjectMocks
        TaskServiceAuth taskServiceAuth;

        @Mock
        TaskRepository taskRepository;

        @Mock
        UserRepository userRepository;

        @Mock
        AuthenticationManager authenticationManager;

        @Mock
        TokenConfig tokenConfig;

        private UserModel user;
        private TaskModel task;
        private UUID taskId;

        @BeforeEach
        void setUp() {
            user = new UserModel();
            user.setLogin("jonathan");

            taskId = UUID.randomUUID();
            task = new TaskModel();
            task.setTitulo("Estudar Testes");
            task.setDescricao("JUnit + Mockito");
            task.setDataInicio(LocalDateTime.now());
            task.setDataFim(LocalDateTime.now().plusDays(1));
            task.setFinalizarTarefa(false);
            task.setIdUser(user);
        }

        // ========================
        // LOGIN
        // ========================

        @Test
        @DisplayName("Deve autenticar e retornar token JWT")
        void login_success() {
            LoginRequestDTO request = new LoginRequestDTO("jonathan", "123456");

            Authentication authentication = mock(Authentication.class);
            when(authentication.getPrincipal()).thenReturn(user);
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(authentication);
            when(tokenConfig.generateToken(user)).thenReturn("jwt-token-mock");

            LoginResponseDTO response = taskServiceAuth.loginAuthToken(request);

            assertThat(response.token()).isEqualTo("jwt-token-mock");
            verify(tokenConfig, times(1)).generateToken(user);
        }

        // ========================
        // CREATE
        // ========================

        @Test
        @DisplayName("Deve criar uma task vinculada ao usuário logado")
        void create_success() {
            TasksRegisterDTO dto = new TasksRegisterDTO(
                    "Estudar Testes",
                    "JUnit + Mockito",
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(1)
            );

            when(userRepository.findByLogin("jonathan")).thenReturn(user);
            when(taskRepository.save(any(TaskModel.class))).thenReturn(task);

            TaskModel result = taskServiceAuth.create(dto, "jonathan");

            assertThat(result).isNotNull();
            assertThat(result.getIdUser().getLogin()).isEqualTo("jonathan");
            verify(taskRepository, times(1)).save(any(TaskModel.class));
        }

        // ========================
        // DELETE
        // ========================

        @Test
        @DisplayName("Deve deletar (soft delete) a task do usuário logado")
        void delete_success() {
            when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

            taskServiceAuth.delete(taskId, "jonathan");

            assertThat(task.getFinalizarTarefa()).isTrue();
            verify(taskRepository, times(1)).save(task);
        }

        @Test
        @DisplayName("Deve lançar TaskNotFoundException ao deletar task inexistente")
        void delete_taskNotFound() {
            when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> taskServiceAuth.delete(taskId, "jonathan"))
                    .isInstanceOf(TaskNotFoundException.class);

            verify(taskRepository, never()).save(any());
        }

        @Test
        @DisplayName("Deve lançar AcessDeniedException ao deletar task de outro usuário")
        void delete_accessDenied() {
            when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

            assertThatThrownBy(() -> taskServiceAuth.delete(taskId, "outro_usuario"))
                    .isInstanceOf(AcessDeniedException.class);

            verify(taskRepository, never()).save(any());
        }

        // ========================
        // PARTIAL UPDATE
        // ========================

        @Test
        @DisplayName("Deve atualizar parcialmente a task do usuário logado")
        void partialUpdate_success() {
            TaskUpdateDTO dto = new TaskUpdateDTO(
                    "Novo Título",
                    null,
                    LocalDateTime.now().plusDays(2)
            );

            when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

            taskServiceAuth.partialUpdate(taskId, dto, "jonathan");

            assertThat(task.getTitulo()).isEqualTo("Novo Título");
            assertThat(task.getDataFim()).isEqualTo(dto.dataFim());
            verify(taskRepository, times(1)).save(task);
        }

        @Test
        @DisplayName("Deve lançar TaskNotFoundException ao atualizar task inexistente")
        void partialUpdate_taskNotFound() {
            TaskUpdateDTO dto = new TaskUpdateDTO("Título", null, null);

            when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> taskServiceAuth.partialUpdate(taskId, dto, "jonathan"))
                    .isInstanceOf(TaskNotFoundException.class);
        }

        @Test
        @DisplayName("Deve lançar AcessDeniedException ao atualizar task de outro usuário")
        void partialUpdate_accessDenied() {
            TaskUpdateDTO dto = new TaskUpdateDTO("Título", null, null);

            when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

            assertThatThrownBy(() -> taskServiceAuth.partialUpdate(taskId, dto, "outro_usuario"))
                    .isInstanceOf(AcessDeniedException.class);

            verify(taskRepository, never()).save(any());
        }
}


