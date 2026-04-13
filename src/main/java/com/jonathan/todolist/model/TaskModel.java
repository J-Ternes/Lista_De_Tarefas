package com.jonathan.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "A data de início da tarefa é obrigatório")
    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @NotBlank(message = "A data de término da tarefa é obrigatório")
    @Column(nullable = false)
    private LocalDateTime dataFim;

     private Boolean finalizarTarefa;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
