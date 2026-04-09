package com.jonathan.todolist.model;

import jakarta.persistence.*;
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
    private String description;

    @Column(length = 50)
    private String tituloTarefa;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String prioridadeDaTarefa;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
