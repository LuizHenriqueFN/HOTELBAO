package com.example.hotelbao.dto;

import com.example.hotelbao.model.Estadia;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class EstadiaValorDTO {
    private String descricaoQuarto;
    private BigDecimal valor;

    public EstadiaValorDTO(Estadia estadia) {
        this.descricaoQuarto = estadia.getQuarto().getDescricao();
        this.valor = estadia.getQuarto().getValor();
    }
}