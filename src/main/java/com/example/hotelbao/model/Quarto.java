package com.example.hotelbao.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "quarto")
public class Quarto extends RepresentationModel<Quarto> {

    @Schema(description = "ID único do quarto gerado pelo banco.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Descrição detalhada do quarto.", example = "Suíte Master com vista para o mar")
    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String descricao;

    @Schema(description = "Valor da diária do quarto.", example = "499.90")
    @NotNull(message = "Valor é obrigatório")
    @Column(nullable = false)
    private BigDecimal valor;

    @Schema(description = "URL de uma imagem do quarto.", example = "https://example.com/images/suite_master.jpg")
    @Column(name = "url_imagem")
    private String urlDaImagem;
}