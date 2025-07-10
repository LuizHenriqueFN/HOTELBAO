package com.example.hotelbao.controller;

import com.example.hotelbao.dto.ErrorResponseDTO;
import com.example.hotelbao.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Tag(name = "- Admin", description = "Operações administrativas restritas.")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Limpa todo o banco de dados", description = "Operação destrutiva que remove todos os clientes, quartos e estadias. Requer permissão de Administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Banco de dados limpo com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Apenas administradores podem executar esta operação.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    @PostMapping("/limpar-banco-de-dados")
    public ResponseEntity<Map<String, String>> limparBancoDeDados() {
        adminService.limparBancoDeDados();
        Map<String, String> response = Map.of("mensagem", "Banco de dados deletado com sucesso");
        return ResponseEntity.ok(response);
    }
}