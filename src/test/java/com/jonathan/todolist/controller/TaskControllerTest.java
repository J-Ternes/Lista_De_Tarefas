package com.jonathan.todolist.controller;

import com.jonathan.todolist.config.JWTUserData;
import com.jonathan.todolist.config.SecurityConfig;
import com.jonathan.todolist.config.SecurityFilter;
import com.jonathan.todolist.dto.TaskResponseDTO;
import com.jonathan.todolist.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;


    @Test
    @DisplayName("Deve retornar 200 e as tasks paginadas do usuário autenticado com ROLE_USER")
    void getAll_shouldReturnTasksPage_whenUserHasRoleUser() throws Exception {
        LocalDateTime dataInicio = LocalDateTime.of(2026, 5, 10, 9, 0);
        LocalDateTime dataFim = LocalDateTime.of(2026, 5, 10, 18, 0);

        TaskResponseDTO dto = new TaskResponseDTO(
                "Estudar Spring Security",
                dataInicio,
                dataFim
        );

        Page<TaskResponseDTO> page = new PageImpl<>(
                List.of(dto),
                PageRequest.of(0, 5, Sort.by("dataInicio").ascending()),
                1
        );

        when(taskService.listMyTasks(eq("jonathan"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(
                        get("/tasks/dados")
                                .with(authenticatedUser("jonathan", "USER"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].titulo").value("Estudar Spring Security"))
                .andExpect(jsonPath("$.size").value(5))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.totalElements").value(1));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(taskService, times(1)).listMyTasks(eq("jonathan"), pageableCaptor.capture());

        Pageable pageableUsed = pageableCaptor.getValue();
        assertThat(pageableUsed.getPageNumber()).isEqualTo(0);
        assertThat(pageableUsed.getPageSize()).isEqualTo(5);
        assertThat(pageableUsed.getSort().getOrderFor("dataInicio")).isNotNull();
        assertThat(pageableUsed.getSort().getOrderFor("dataInicio").getDirection())
                .isEqualTo(Sort.Direction.ASC);
    }

    @Test
    @DisplayName("Deve aplicar paginação customizada quando page, size e sort forem enviados")
    void getAll_shouldUseCustomPageableParams() throws Exception {
        Page<TaskResponseDTO> emptyPage = new PageImpl<>(
                List.of(),
                PageRequest.of(1, 3, Sort.by("dataFim").descending()),
                0
        );

        when(taskService.listMyTasks(eq("jonathan"), any(Pageable.class))).thenReturn(emptyPage);

        mockMvc.perform(
                        get("/tasks/dados")
                                .param("page", "1")
                                .param("size", "3")
                                .param("sort", "dataFim,desc")
                                .with(authenticatedUser("jonathan", "USER"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.size").value(3))
                .andExpect(jsonPath("$.number").value(1));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(taskService, times(1)).listMyTasks(eq("jonathan"), pageableCaptor.capture());

        Pageable pageableUsed = pageableCaptor.getValue();
        assertThat(pageableUsed.getPageNumber()).isEqualTo(1);
        assertThat(pageableUsed.getPageSize()).isEqualTo(3);
        assertThat(pageableUsed.getSort().getOrderFor("dataFim")).isNotNull();
        assertThat(pageableUsed.getSort().getOrderFor("dataFim").getDirection())
                .isEqualTo(Sort.Direction.DESC);
    }

    @Test
    @DisplayName("Deve retornar 401 quando acessar sem autenticação")
    void getAll_shouldReturnUnauthorized_whenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/tasks/dados"))
                .andExpect(status().isUnauthorized());

        verify(taskService, never()).listMyTasks(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("Deve retornar 403 quando o usuário não tiver ROLE_USER")
    void getAll_shouldReturnForbidden_whenUserDoesNotHaveRoleUser() throws Exception {
        mockMvc.perform(
                        get("/tasks/dados")
                                .with(authenticatedUser("jonathan", "ADMIN"))
                )
                .andExpect(status().isForbidden());

        verify(taskService, never()).listMyTasks(anyString(), any(Pageable.class));
    }

    // ========================
    // HELPER
    // ========================

    private RequestPostProcessor authenticatedUser(String login, String... roles) {
        JWTUserData principal = mock(JWTUserData.class);
        when(principal.login()).thenReturn(login);

        List<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        return authentication(auth);
    }
}