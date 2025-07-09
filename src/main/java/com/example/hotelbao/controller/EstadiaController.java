package com.example.hotelbao.controller;

import com.example.hotelbao.model.Estadia;
import com.example.hotelbao.service.EstadiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Estadias", description = "Endpoints para lançamento e consulta de estadias")
@RestController
@RequestMapping("/estadias")
public class EstadiaController {

    @Autowired
    private EstadiaService estadiaService;

    @Operation(summary = "Lança uma nova estadia (faz uma reserva)")
    @PostMapping
    public Estadia lancarEstadia(@RequestBody Estadia estadia) {
        return estadiaService.salvar(estadia);
    }

    @Operation(summary = "Lista todas as estadias cadastradas")
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

    @Operation(summary = "Busca uma estadia específica por ID")
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
}