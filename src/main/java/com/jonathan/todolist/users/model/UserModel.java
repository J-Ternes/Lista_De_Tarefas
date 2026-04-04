package com.jonathan.todolist.users.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Locale;
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
    private String password;

    @Column(nullable = false)
    private String role;

    @CreationTimestamp //Cria automaticamente quando o dado for criado
    private LocalDateTime createdAt;


}
