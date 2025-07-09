package com.example.hotelbao.service;

import com.example.hotelbao.model.Quarto;
import com.example.hotelbao.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    public Quarto salvarQuarto(Quarto quarto) {
        return quartoRepository.save(quarto);
    }

    public List<Quarto> listarTodos() {
        return quartoRepository.findAll();
    }

    public Optional<Quarto> buscarPorId(Long id) {
        return quartoRepository.findById(id);
    }

    public Quarto alterarQuarto(Long id, Quarto quartoDetails) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado com id: " + id));

        quarto.setDescricao(quartoDetails.getDescricao());
        quarto.setValor(quartoDetails.getValor());
        quarto.setUrlDaImagem(quartoDetails.getUrlDaImagem());

        return quartoRepository.save(quarto);
    }

    public void deletarQuarto(Long id) {
        quartoRepository.deleteById(id);
    }
}