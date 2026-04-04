package com.jonathan.todolist.users.repository;

import com.jonathan.todolist.users.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {


    UserDetails findByLogin(String login);


}
