package com.example.hotelbao.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemEstadiaDTO {
    private String quarto;
    private BigDecimal valor;
}