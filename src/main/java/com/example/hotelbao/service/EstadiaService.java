package com.example.hotelbao.service;

import com.example.hotelbao.dto.ItemEstadiaDTO;
import com.example.hotelbao.dto.NotaFiscalDTO;
import com.example.hotelbao.exception.ValidacaoException;
import com.example.hotelbao.model.Cliente;
import com.example.hotelbao.model.Estadia;
import com.example.hotelbao.repository.ClienteRepository;
import com.example.hotelbao.repository.EstadiaRepository;
import com.example.hotelbao.repository.QuartoRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadiaService {

    @Autowired
    private EstadiaRepository estadiaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    public Estadia salvar(Estadia estadia) {
        clienteRepository.findById(estadia.getCliente().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cliente com ID " + estadia.getCliente().getId() + " não encontrado."));
        quartoRepository.findById(estadia.getQuarto().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Quarto com ID " + estadia.getQuarto().getId() + " não encontrado."));

        Long quartoId = estadia.getQuarto().getId();
        LocalDate data = estadia.getDataEstadia();
        Long estadiaId = estadia.getId();

        if (estadiaRepository.existsConflict(quartoId, data, estadiaId)) {
            throw new ValidacaoException("Este quarto já está reservado para esta data.");
        }

        return estadiaRepository.save(estadia);
    }

    public List<Estadia> listarTodos() {
        return estadiaRepository.findAll();
    }

    public Optional<Estadia> buscarPorId(Long id) {
        return estadiaRepository.findById(id);
    }

    public List<Estadia> listarEstadiasPorCliente(Long clienteId) {
        return estadiaRepository.findByClienteId(clienteId);
    }

    public Optional<Estadia> buscarEstadiaMaisCaraPorCliente(Long clienteId) {
        return estadiaRepository.findTopByClienteIdOrderByQuartoValorDesc(clienteId);
    }

    public Optional<Estadia> buscarEstadiaMaisBarataPorCliente(Long clienteId) {
        return estadiaRepository.findTopByClienteIdOrderByQuartoValorAsc(clienteId);
    }

    public Optional<BigDecimal> calcularTotalEstadiasPorCliente(Long clienteId) {
        return estadiaRepository.sumValorEstadiasByClienteId(clienteId);
    }

    public NotaFiscalDTO gerarNotaFiscal(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ValidacaoException("Cliente não encontrado."));

        if (cliente.getNome() == null || cliente.getNome().isBlank() ||
                cliente.getEndereco() == null || cliente.getEndereco().isBlank()) {
            throw new ValidacaoException("Dados obrigatórios do cliente não foram preenchidos.");
        }

        List<Estadia> estadias = estadiaRepository.findByClienteId(clienteId);

        if (estadias.isEmpty()) {
            throw new ValidacaoException("Não existem estadias com valor e descrição para este cliente.");
        }

        NotaFiscalDTO notaFiscal = new NotaFiscalDTO();
        notaFiscal.setNomeCliente(cliente.getNome());
        notaFiscal.setEnderecoCliente(cliente.getEndereco());

        List<ItemEstadiaDTO> itens = estadias.stream()
                .map(estadia -> {
                    ItemEstadiaDTO item = new ItemEstadiaDTO();
                    item.setQuarto(estadia.getQuarto().getDescricao());
                    item.setValor(estadia.getQuarto().getValor());
                    return item;
                }).collect(Collectors.toList());

        notaFiscal.setEstadias(itens);

        BigDecimal total = itens.stream()
                .map(ItemEstadiaDTO::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        notaFiscal.setTotal(total);

        return notaFiscal;
    }
}
