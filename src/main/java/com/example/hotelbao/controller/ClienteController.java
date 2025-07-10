package com.example.hotelbao.controller;

import com.example.hotelbao.model.Cliente;
import com.example.hotelbao.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Insere um novo cliente (cria uma conta)")
    @PostMapping
    public Cliente inserirCliente(@RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @Operation(summary = "Lista todos os clientes cadastrados")
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        for (Cliente cliente : clientes) {
            cliente.add(linkTo(methodOn(ClienteController.class).buscarClientePorId(cliente.getId())).withSelfRel());
        }
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Busca um cliente específico por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(cliente -> {
                    cliente.add(
                            linkTo(methodOn(ClienteController.class).listarClientes()).withRel("todos-os-clientes"));
                    return ResponseEntity.ok(cliente);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Altera os dados de um cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> alterarCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        try {
            Cliente clienteAtualizado = clienteService.alterar(id, clienteDetails);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deleta um cliente existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retorna os dados do cliente autenticado")
    @GetMapping("/me")
    public ResponseEntity<Cliente> getAuthenticatedCliente(Authentication authentication) {
        String login = authentication.getName();
        return clienteService.buscarPorLogin(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}