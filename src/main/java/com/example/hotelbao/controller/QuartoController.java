package com.example.hotelbao.controller;

import com.example.hotelbao.dto.ErrorResponseDTO;
import com.example.hotelbao.model.Quarto;
import com.example.hotelbao.service.QuartoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "- Quartos", description = "Endpoints para visualização e gerenciamento de quartos.")
@RestController
@RequestMapping("/quartos")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @Operation(summary = "Cria um novo quarto (Apenas Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quarto criado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))), })
    @PostMapping
    public Quarto inserirQuarto(@RequestBody Quarto quarto) {
        return quartoService.salvarQuarto(quarto);
    }

    @Operation(summary = "Lista todos os quartos disponíveis (Público)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de quartos retornada.") })
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

    @Operation(summary = "Busca um quarto por ID (Público)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quarto encontrado."),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))) })
    @GetMapping("/{id}")
    public ResponseEntity<Quarto> buscarQuartoPorId(@PathVariable Long id) {
        return quartoService.buscarPorId(id)
                .map(quarto -> {
                    quarto.add(linkTo(methodOn(QuartoController.class).listarQuartos()).withRel("todos-os-quartos"));
                    return ResponseEntity.ok(quarto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Altera um quarto existente (Apenas Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quarto atualizado."),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))) })
    @PutMapping("/{id}")
    public ResponseEntity<Quarto> alterarQuarto(@PathVariable Long id, @RequestBody Quarto quartoDetails) {
        try {
            Quarto quartoAtualizado = quartoService.alterarQuarto(id, quartoDetails);
            return ResponseEntity.ok(quartoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deleta um quarto (Apenas Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Quarto deletado."),
            @ApiResponse(responseCode = "404", description = "Quarto não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuarto(@PathVariable Long id) {
        quartoService.deletarQuarto(id);
        return ResponseEntity.noContent().build();
    }
}