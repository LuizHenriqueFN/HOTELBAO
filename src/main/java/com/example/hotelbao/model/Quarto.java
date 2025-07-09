package com.example.hotelbao.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "quarto")
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "url_imagem")
    private String urlDaImagem;
}
