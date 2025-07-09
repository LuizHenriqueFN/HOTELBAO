package com.example.hotelbao.controller;

import com.example.hotelbao.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/limpar-banco-de-dados")
    public ResponseEntity<Map<String, String>> limparBancoDeDados() {
        adminService.limparBancoDeDados();
        Map<String, String> response = Map.of("mensagem", "Banco de dados deletado com sucesso");
        return ResponseEntity.ok(response);
    }
}