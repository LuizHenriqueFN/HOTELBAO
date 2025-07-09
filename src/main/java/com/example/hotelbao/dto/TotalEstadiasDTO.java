package com.example.hotelbao.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TotalEstadiasDTO {
    private BigDecimal total;

    public TotalEstadiasDTO(BigDecimal total) {
        this.total = total != null ? total : BigDecimal.ZERO;
    }
}
