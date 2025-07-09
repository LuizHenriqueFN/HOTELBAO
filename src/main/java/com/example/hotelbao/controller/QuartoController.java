package com.example.hotelbao.controller;

import com.example.hotelbao.model.Quarto;
import com.example.hotelbao.service.QuartoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Quartos", description = "Endpoints para gerenciamento de quartos")
@RestController
@RequestMapping("/quartos") // Define o caminho base para todos os endpoints deste controller
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @Operation(summary = "Insere um novo quarto")
    @PostMapping
    public Quarto inserirQuarto(@RequestBody Quarto quarto) {
        return quartoService.salvarQuarto(quarto);
    }

    @Operation(summary = "Lista todos os quartos cadastrados com links HATEOAS")
    @GetMapping
    public ResponseEntity<List<Quarto>> listarQuartos() {
        List<Quarto> quartos = quartoService.listarTodos();
        if (quartos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        for (Quarto quarto : quartos) {
            long id = quarto.getId();
            quarto.add(linkTo(methodOn(QuartoController.class).buscarQuartoPorId(id)).withSelfRel());
        }
        return ResponseEntity.ok(quartos);
    }

    @Operation(summary = "Busca um quarto específico por ID com links HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscarQuartoPorId(@PathVariable Long id) {
        return quartoService.buscarPorId(id)
                .map(quarto -> {
                    quarto.add(linkTo(methodOn(QuartoController.class).listarQuartos()).withRel("todos-os-quartos"));
                    return ResponseEntity.ok(quarto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Altera os dados de um quarto existente")
    @PutMapping("/{id}")
    public ResponseEntity<Quarto> alterarQuarto(@PathVariable Long id, @RequestBody Quarto quartoDetails) {
        try {
            Quarto quartoAtualizado = quartoService.alterarQuarto(id, quartoDetails);
            return ResponseEntity.ok(quartoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deleta um quarto existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuarto(@PathVariable Long id) {
        quartoService.deletarQuarto(id);
        return ResponseEntity.noContent().build();
    }
}