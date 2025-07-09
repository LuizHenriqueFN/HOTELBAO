package com.example.hotelbao.service;

import com.example.hotelbao.repository.ClienteRepository;
import com.example.hotelbao.repository.EstadiaRepository;
import com.example.hotelbao.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @Autowired
    private EstadiaRepository estadiaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Transactional
    public void limparBancoDeDados() {
        estadiaRepository.deleteAll();
        clienteRepository.deleteAll();
        quartoRepository.deleteAll();
    }
}