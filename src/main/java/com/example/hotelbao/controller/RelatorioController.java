package com.example.hotelbao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelbao.dto.EstadiaValorDTO;
import com.example.hotelbao.dto.NotaFiscalDTO;
import com.example.hotelbao.dto.TotalEstadiasDTO;
import com.example.hotelbao.service.EstadiaService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private EstadiaService estadiaService;

    @GetMapping("/cliente/{clienteId}/maior-valor")
    public ResponseEntity<?> getEstadiaMaiorValor(@PathVariable Long clienteId) {
        return estadiaService.buscarEstadiaMaisCaraPorCliente(clienteId)
                .map(estadia -> ResponseEntity.ok(new EstadiaValorDTO(estadia)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}/menor-valor")
    public ResponseEntity<?> getEstadiaMenorValor(@PathVariable Long clienteId) {
        return estadiaService.buscarEstadiaMaisBarataPorCliente(clienteId)
                .map(estadia -> ResponseEntity.ok(new EstadiaValorDTO(estadia)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}/total")
    public ResponseEntity<TotalEstadiasDTO> getTotalEstadias(@PathVariable Long clienteId) {
        BigDecimal total = estadiaService.calcularTotalEstadiasPorCliente(clienteId).orElse(BigDecimal.ZERO);
        return ResponseEntity.ok(new TotalEstadiasDTO(total));
    }

    @GetMapping("/cliente/{clienteId}/nota-fiscal")
    public ResponseEntity<NotaFiscalDTO> getNotaFiscal(@PathVariable Long clienteId) {
        NotaFiscalDTO notaFiscal = estadiaService.gerarNotaFiscal(clienteId);
        return ResponseEntity.ok(notaFiscal);
    }
}
