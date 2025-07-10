package com.example.hotelbao.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "estadia")
public class Estadia extends RepresentationModel<Estadia> {

    @Schema(description = "ID único da estadia gerado pelo banco.", example = "101", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Cliente associado à estadia. Ao criar, forneça apenas o 'id' do cliente.")
    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Schema(description = "Quarto reservado na estadia. Ao criar, forneça apenas o 'id' do quarto.")
    @NotNull(message = "Quarto é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "quarto_id", nullable = false)
    private Quarto quarto;

    @Schema(description = "Data de início da diária da estadia.", example = "2025-12-25")
    @NotNull(message = "Data da estadia é obrigatória")
    @Column(nullable = false)
    private LocalDate dataEstadia;
}