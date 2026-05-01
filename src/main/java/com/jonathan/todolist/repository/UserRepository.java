package com.jonathan.todolist.repository;

import com.jonathan.todolist.model.UserModel;
import com.jonathan.todolist.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByLogin(String login);
    List<UserModel> findByRole(UserRole role);
    List<UserModel> findByActiveTrue();
    UserModel findByLoginAndActiveTrue(String login);
}
