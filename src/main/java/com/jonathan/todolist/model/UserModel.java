package com.jonathan.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserModel implements UserDetails {

    @Id //Informa que essa será a minha chave primária
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O campo login é obrigatório!")
    @Column(unique = true, nullable = false)
    private String login;

    @NotBlank(message = "O campo password é obrigatório!")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private Boolean active; //Para dizer se o usuário está ativo ou não

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @OneToMany(mappedBy = "idUser", cascade = CascadeType.ALL, orphanRemoval = true) //Quando o usuário for eliminado, as cascatas também serão
    private List<TaskModel> tasks;

    @PrePersist
    public void prePersist(){
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate // 👈 estava faltando isso
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }


    //Configuração Spring security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       if(this.role == UserRole.ADMIN){
           return List.of(
                   new SimpleGrantedAuthority("ROLE_ADMIN"),
                   new SimpleGrantedAuthority("ROLE_USER")
           );
       }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}