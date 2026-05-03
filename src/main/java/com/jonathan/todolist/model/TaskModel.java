package com.jonathan.todolist.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100)
    private String descricao;

    @NotBlank(message = "O título da tarefa é obrigatório")
    @Column(length = 50,nullable = false)
    private String titulo;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull(message = "O campo de data início é obrigatório!")
    @Column(nullable = false, name = "data_inicio")
    private LocalDateTime dataInicio;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull(message = "O campo de data final é obrigatório!")
    @Column(nullable = false, name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "finalizar_tarefa")
     private Boolean finalizarTarefa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel idUser;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist(){
        criadoEm = LocalDateTime.now();
    }

}
