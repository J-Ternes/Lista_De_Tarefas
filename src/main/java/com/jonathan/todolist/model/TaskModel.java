package com.jonathan.todolist.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(length = 100)
    private String descricao;

    @NotBlank(message = "O título da tarefa é obrigatório")
    @Column(length = 50,nullable = false)
    private String titulo;

    @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")
    @NotNull(message = "O campo de data início é obrigatório!")
    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")
    @NotNull(message = "O campo de data final é obrigatório!")
    @Column(nullable = false)
    private LocalDateTime dataFim;

     private Boolean finalizarTarefa;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
