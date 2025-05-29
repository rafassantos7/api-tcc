package com.example.aula.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.aula.model.Metas;
import com.example.aula.repository.MetasRepository;

import jakarta.validation.Valid;

@Service
@Validated
public class MetasService {
    private final MetasRepository metasRepository;
    public MetasService(MetasRepository metasRepository) {
        this.metasRepository = metasRepository;
    }

    public List<Metas> listarTodos() {
        return metasRepository.findAll();
    }

    public Metas salvar(@Valid Metas metas) {
        return metasRepository.save(metas);
    }

}
