package com.example.hotelbao.controller;

import com.example.hotelbao.dto.ErrorResponseDTO;
import com.example.hotelbao.model.Estadia;
import com.example.hotelbao.service.EstadiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "- Estadias", description = "Endpoints para gerenciamento de estadias (reservas).")
@RestController
@RequestMapping("/estadias")
public class EstadiaController {

    @Autowired
    private EstadiaService estadiaService;

    @Operation(summary = "Cria uma nova estadia (reserva)", description = "Requer autenticação como Cliente ou Admin. Valida se a data é futura e se o quarto está disponível.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação (ex: quarto já reservado, data passada).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public Estadia lancarEstadia(@RequestBody Estadia estadia) {
        return estadiaService.salvar(estadia);
    }

    @Operation(summary = "Lista todas as estadias cadastradas (Apenas Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estadias retornada."),
            @ApiResponse(responseCode = "204", description = "Nenhuma estadia encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<Estadia>> listarEstadias() {
        List<Estadia> estadias = estadiaService.listarTodos();
        if (estadias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        for (Estadia estadia : estadias) {
            estadia.add(linkTo(methodOn(EstadiaController.class).buscarEstadiaPorId(estadia.getId())).withSelfRel());
        }
        return ResponseEntity.ok(estadias);
    }

    @Operation(summary = "Busca uma estadia específica por ID (Apenas Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadia encontrada."),
            @ApiResponse(responseCode = "404", description = "Estadia não encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Estadia> buscarEstadiaPorId(@PathVariable Long id) {
        return estadiaService.buscarPorId(id)
                .map(estadia -> {
                    estadia.add(
                            linkTo(methodOn(EstadiaController.class).listarEstadias()).withRel("todas-as-estadias"));
                    return ResponseEntity.ok(estadia);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta uma estadia existente (Apenas Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estadia deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Estadia não encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstadia(@PathVariable Long id) {
        estadiaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}