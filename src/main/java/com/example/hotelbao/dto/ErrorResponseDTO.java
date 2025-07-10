package com.example.hotelbao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorResponseDTO {

    @Schema(description = "Timestamp da ocorrência do erro.", example = "1752276900000")
    private long timestamp;

    @Schema(description = "Código de status HTTP.", example = "404")
    private int status;

    @Schema(description = "Tipo do erro.", example = "Not Found")
    private String error;

    @Schema(description = "Mensagem detalhada do erro.", example = "Cliente com ID 100 não encontrado.")
    private String message;

    @Schema(description = "Caminho da requisição que gerou o erro.", example = "/clientes/100")
    private String path;
}