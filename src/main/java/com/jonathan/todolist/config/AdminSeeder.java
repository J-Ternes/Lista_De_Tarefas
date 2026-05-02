package com.jonathan.todolist.config;

import com.jonathan.todolist.model.UserModel;
import com.jonathan.todolist.model.UserRole;
import com.jonathan.todolist.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class AdminSeeder {

    //Criação do primeiro admin do banco
    @Bean
    CommandLineRunner seedAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(userRepository.findByRole(UserRole.ADMIN).isEmpty()) {
                UserModel admin = new UserModel();
                admin.setRole(UserRole.ADMIN);
                admin.setActive(true);
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setLogin("JonathanDev");
                userRepository.save(admin);
            }
        };
    }
}
