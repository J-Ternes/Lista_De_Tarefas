package com.jonathan.todolist.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_users")
public class UserModel {

    @Id //Informa que essa será a minha chave primária
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;


    private boolean active = true; //Para dizer se o usuário está ativo ou não

    @CreationTimestamp //Cria automaticamente quando o dado for criado
    private LocalDateTime createdAt;

}