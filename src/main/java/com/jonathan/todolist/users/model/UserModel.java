package com.jonathan.todolist.users.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_users")
//O UserDetails vem do Spring Secutiry e mostra que essa classe precisará ser autenticada
public class UserModel implements UserDetails  {

    @Id //Informa que essa será a minha chave primária
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @CreationTimestamp //Cria automaticamente quando o dado for criado
    private LocalDateTime createdAt;

    public UserModel(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Caso o login seja de admin, ele poderá ter as permissões de admin e usuario
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        //Caso o login nao seja de admin, ele terá permissoes apenas de usuario
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }
}
