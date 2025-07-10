package com.example.hotelbao.controller;

import com.example.hotelbao.dto.ErrorResponseDTO;
import com.example.hotelbao.dto.EstadiaValorDTO;
import com.example.hotelbao.dto.NotaFiscalDTO;
import com.example.hotelbao.dto.TotalEstadiasDTO;
import com.example.hotelbao.service.EstadiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "- Relatórios", description = "Endpoints para geração de relatórios de estadias por cliente.")
@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

        @Autowired
        private EstadiaService estadiaService;

        @Operation(summary = "Relatório da estadia de MAIOR valor de um cliente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Cliente ou estadias não encontrados.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
                        @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        })
        @GetMapping("/cliente/{clienteId}/maior-valor")
        public ResponseEntity<?> getEstadiaMaiorValor(
                        @Parameter(description = "ID do cliente para o relatório", example = "1") @PathVariable Long clienteId) {
                return estadiaService.buscarEstadiaMaisCaraPorCliente(clienteId)
                                .map(estadia -> ResponseEntity.ok(new EstadiaValorDTO(estadia)))
                                .orElse(ResponseEntity.notFound().build());
        }

        @Operation(summary = "Relatório da estadia de MENOR valor de um cliente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso."),
                        @ApiResponse(responseCode = "404", description = "Cliente ou estadias não encontrados.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
                        @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        })
        @GetMapping("/cliente/{clienteId}/menor-valor")
        public ResponseEntity<?> getEstadiaMenorValor(
                        @Parameter(description = "ID do cliente para o relatório", example = "1") @PathVariable Long clienteId) {
                return estadiaService.buscarEstadiaMaisBarataPorCliente(clienteId)
                                .map(estadia -> ResponseEntity.ok(new EstadiaValorDTO(estadia)))
                                .orElse(ResponseEntity.notFound().build());
        }

        @Operation(summary = "Relatório do valor TOTAL das estadias de um cliente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso."),
                        @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
        })
        @GetMapping("/cliente/{clienteId}/total")
        public ResponseEntity<TotalEstadiasDTO> getTotalEstadias(
                        @Parameter(description = "ID do cliente para o relatório", example = "1") @PathVariable Long clienteId) {
                BigDecimal total = estadiaService.calcularTotalEstadiasPorCliente(clienteId).orElse(BigDecimal.ZERO);
                return ResponseEntity.ok(new TotalEstadiasDTO(total));
        }

        @Operation(summary = "Gera uma NOTA FISCAL para um cliente com todas as suas estadias")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Nota fiscal gerada com sucesso."),
                        @ApiResponse(responseCode = "400", description = "Erro de validação (ex: dados do cliente incompletos).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
                        @ApiResponse(responseCode = "403", description = "Acesso negado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
        })
        @GetMapping("/cliente/{clienteId}/nota-fiscal")
        public ResponseEntity<NotaFiscalDTO> getNotaFiscal(
                        @Parameter(description = "ID do cliente para a nota fiscal", example = "1") @PathVariable Long clienteId) {
                NotaFiscalDTO notaFiscal = estadiaService.gerarNotaFiscal(clienteId);
                return ResponseEntity.ok(notaFiscal);
        }
}