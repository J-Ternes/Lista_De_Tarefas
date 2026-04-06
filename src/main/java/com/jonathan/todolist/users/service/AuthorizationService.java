package com.jonathan.todolist.users.service;

import com.jonathan.todolist.configurations.security.TokenService;
import com.jonathan.todolist.users.exceptions.UserFoundException;
import com.jonathan.todolist.users.model.AuthenticationDTO;
import com.jonathan.todolist.users.model.RegisterDTO;
import com.jonathan.todolist.users.model.UserModel;
import com.jonathan.todolist.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserFoundException userFoundException;

    public String LoginAuthorization(AuthenticationDTO data){
        //Recebe as credenciais
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        //Verifica se as credenciais são verdadeiras buscando o usuario no banco de dados (UserDetailsService) e depois compara a senha com o encode
        var auth = this.authenticationManager.authenticate(usernamePassword);

        //Retorna o objeto do usuario autenticado e gera um token com as suas informações
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return token;
    }

    public UserModel NewUser(RegisterDTO data){
        if(this.userRepository.findByLogin(data.login()) != null) {
            throw new UserFoundException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password()); //Irá criptografar a senha
        UserModel newUser = new UserModel(data.login(), encryptedPassword, data.role());

        return this.userRepository.save(newUser);
    }

    //Irá consultar os logins dos usuarios na tabela do banco de dados de users
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }
}
