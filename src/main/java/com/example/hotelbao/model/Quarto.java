package com.example.hotelbao.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true) // Adicione o (callSuper = true)
@Entity
@Table(name = "quarto")
public class Quarto extends RepresentationModel<Quarto> {
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
