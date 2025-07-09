package com.example.hotelbao.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class NotaFiscalDTO {
    private String nomeCliente;
    private String enderecoCliente;
    private List<ItemEstadiaDTO> estadias;
    private BigDecimal total;
}
