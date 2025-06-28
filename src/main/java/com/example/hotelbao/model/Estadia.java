package com.example.hotelbao.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "estadia")
public class Estadia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "Quarto é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(name = "quarto_id", nullable = false)
    private Quarto quarto;

    @NotNull(message = "Data da estadia é obrigatória")
    @Column(nullable = false)
    private LocalDate dataEstadia;
}
