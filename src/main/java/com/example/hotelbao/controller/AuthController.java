package com.example.hotelbao.controller;

import com.example.hotelbao.dto.LoginRequestDTO;
import com.example.hotelbao.dto.LoginResponseDTO;
import com.example.hotelbao.model.Cliente;
import com.example.hotelbao.repository.ClienteRepository;
import com.example.hotelbao.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "Autenticação")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        if ("Admin".equals(loginRequest.getLogin()) && "ADM123".equals(loginRequest.getSenha())) {
            String token = tokenProvider.generateToken("Admin", List.of("ROLE_ADMIN"));
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }

        Optional<Cliente> clienteOpt = clienteRepository.findByLogin(loginRequest.getLogin());
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            // if (passwordEncoder.matches(loginRequest.getSenha(), cliente.getSenha())) {
            if (loginRequest.getSenha().equals(cliente.getSenha())) {
                String token = tokenProvider.generateToken(cliente.getLogin(), List.of("ROLE_CLIENTE"));
                return ResponseEntity.ok(new LoginResponseDTO(token));
            }
        }

        return ResponseEntity.status(401).body("Login ou senha inválidos!");
    }
}